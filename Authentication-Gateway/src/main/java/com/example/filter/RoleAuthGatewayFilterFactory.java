package com.example.filter;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.example.util.JwtUtil;

import lombok.Data;

@Component
public class RoleAuthGatewayFilterFactory extends
        AbstractGatewayFilterFactory<RoleAuthGatewayFilterFactory.Config> {
	
	@Autowired
	private JwtUtil jwtUtil;
	
    public RoleAuthGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            var request = exchange.getRequest();
            // JWTUtil can extract the token from the request, parse it and verify if the given role is available
            if(!jwtUtil.hasRole(request, config.getRole())){
                // seems we miss the auth token
                var response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
            return chain.filter(exchange);
        };
    }

    @Data
    public static class Config {
        private String role;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        // we need this to use shortcuts in the application.yml
        return Arrays.asList("role");
    }
}
