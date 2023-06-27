package com.example.config;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.example.entities.Providers;
import com.example.entities.RoleAssociation;

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
		return extractClaim(token, Claims::getSubject);		
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}	
	
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
	
	public String generateToken(Providers providers, List<RoleAssociation> roleAssociation) {
		Map<String, Object> map= new HashMap<>();
		map.put("provider_name", providers.getProvider_name());
		map.put("provider_code", providers.getProvider_code());
		map.put("username", providers.getUsername());
		map.put("email", providers.getEmail());
		
		List<String> roles = new ArrayList<>();		
		for(RoleAssociation r :roleAssociation) {
			roles.add(r.getRole().getName());
		}
		
		map.put("roles", roles);
		return generateToken(map,providers);
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
			return extractClaim(token, Claims::getExpiration).before(new Date());
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
	
	public void validateToken(final String token) {
		extractAllClaims(token);
	}
}
