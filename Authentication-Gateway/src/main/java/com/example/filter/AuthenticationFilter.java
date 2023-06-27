package com.example.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

import com.example.util.JwtUtil;
import com.google.common.net.HttpHeaders;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>{
	
	@Autowired
	private RouteValidator routeValidator;
	
	@Autowired
	private JwtUtil jwtUtil;
	
//	@Autowired
//	private RestTemplate restTemplate;
	
	public AuthenticationFilter() {
		super(Config.class);
	}
	
	@Override
	public GatewayFilter apply(Config config) {		
		return ((exchange,chain)->{
			ServerHttpRequest request = null;
			if(routeValidator.isSecured.test(exchange.getRequest())) {
				if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					throw new RuntimeException("Auth Header not found");
				}
				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
				if(authHeader != null && authHeader.startsWith("Bearer ")) {
					authHeader = authHeader.substring(7);					
				}
				try {
//					restTemplate.getForObject("http://AUTHENTICATION//validate?token+authHeader", String.class);
					jwtUtil.validateToken(authHeader);
					
					Claims claims = jwtUtil.extractAllClaims(authHeader);											
					
					request = exchange.getRequest().mutate()
							.header("provider_name", claims.get("provider_name").toString())
							.header("provider_code",  claims.get("provider_code").toString())
							.header("username", claims.get("username").toString())
							.header("email", claims.get("email").toString())
							.build();										
				}
				catch (MalformedJwtException ex) {
					System.out.println("Invalid JWT token");
				} catch (ExpiredJwtException ex) {
					System.out.println("Expired JWT token");
				}  catch (IllegalArgumentException ex) {
					System.out.println("JWT claims string is empty.");
				}  catch(Exception e) {
					e.printStackTrace();
					System.out.println("Invalid JWT token");
				}				
			}
			return chain.filter(exchange.mutate().request(request).build());
		});
	}
	
	public static class Config{
		
	}
}
