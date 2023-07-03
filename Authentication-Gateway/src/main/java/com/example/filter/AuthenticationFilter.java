package com.example.filter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import com.example.util.JwtUtil;
import com.google.common.net.HttpHeaders;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>{
	
	 Logger logger = LoggerFactory.getLogger(JwtUtil.class);
	
	@Autowired
	private RouteValidator routeValidator;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	public AuthenticationFilter() {
		super(Config.class);
	}
	
	@Override
	public GatewayFilter apply(Config config) {		
		return ((exchange,chain)->{
			ServerHttpRequest request = null;
			if(routeValidator.isSecured.test(exchange.getRequest())) {
				if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					throw new NullPointerException("Auth Header not found");
				}
				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
				if(authHeader != null && authHeader.startsWith("Bearer ")) {
					authHeader = authHeader.substring(7);					
				}
				try {
					jwtUtil.validateToken(authHeader);
					
					Claims claims = jwtUtil.extractAllClaims(authHeader);											
					List<String> list = (List<String>) claims.get("roles");				
					String[] roles = list.toArray(new String[0]);
					
					request = exchange.getRequest().mutate()
							.header("name", claims.get("name").toString())
							.header("node",  claims.get("code").toString())
							.header("username", claims.get("username").toString())
							.header("email", claims.get("email").toString())
							.header("roles", roles)
							.build();										
				}
				catch (MalformedJwtException ex) {
					logger.error("Invalid Jwt Token",ex);
				} catch (ExpiredJwtException ex) {
					ex.printStackTrace();
				}  catch (IllegalArgumentException ex) {
					ex.printStackTrace();
				}  catch(Exception e) {
					e.printStackTrace();				
				}				
			}
			return chain.filter(exchange.mutate().request(request).build());
		});
	}
	
	public static class Config{
		
	}
}
