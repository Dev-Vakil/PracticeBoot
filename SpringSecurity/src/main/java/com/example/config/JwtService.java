package com.example.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.entities.Providers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private String jwtSecret = "25442A472D4B6150645367566B58703273357638792F423F4528482B4D625165";
	
	public String extractEmail(String token) {
		Claims claims = Jwts.parserBuilder()
		.setSigningKey(getSignInKey())
		.build()
		.parseClaimsJws(token)
		.getBody();
		String email = claims.getSubject();
		return email;
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String generateToken(Providers providers) {
		return generateToken(new HashMap<>(),providers);
	}
	
	public String generateToken(Map<String, Object> extraClaims,Providers providers) {
		return Jwts
				.builder()
				.addClaims(extraClaims)
				.setSubject(providers.getEmail())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24 *7))
				.signWith(getSignInKey())
				.compact();
	}
	
	public boolean isTokenValid(String token, Providers providers) {		
		final String useremail = extractEmail(token);		
		return (useremail).equals(providers.getEmail()) && !isTokenExpired(token);
	}

	public boolean isTokenExpired(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()	
				.parseClaimsJws(token)
				.getBody()
				.getExpiration().before(new Date());
//			return true;
		} catch (MalformedJwtException ex) {
			System.out.println("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			System.out.println("Expired JWT token");
		}  catch (IllegalArgumentException ex) {
			System.out.println("JWT claims string is empty.");
		}  catch(Exception e) {
			e.printStackTrace();
			System.out.println("Invalid JWT token");
		}
		return false;
	}
}
