package com.example.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import com.google.common.net.HttpHeaders;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtil {
	
	private String jwtSecret = "25442A472D4B6150645367566B58703273357638792F423F4528482B4D625165";
	
	public Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public void validateToken(final String token) {
		extractAllClaims(token);
	}

	public boolean hasRole(ServerHttpRequest request, String role) {
		Claims claims = extractAllClaims(request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0).substring(7));
		List<String> roles =  (List<String>) claims.get("roles");
		if(roles.contains(role)) {
			return true;
		}		
		return false;
	}
}
