package com.example.config;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.example.dto.UserDto;
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
	
	 Logger logger
     = LoggerFactory.getLogger(JwtService.class);
	
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
	
	public String generateToken(UserDto details, List<RoleAssociation> roleAssociation) {		
		Map<String, Object> map= new HashMap<>();
		map.put("name", details.getName());
		map.put("code", details.getCode());
		map.put("username", details.getUsername());
		map.put("email", details.getEmail());			
		
		List<String> roles = new ArrayList<>();		
		for(RoleAssociation r :roleAssociation) {
			roles.add(r.getRole().getName());
		}
			
		map.put("roles", roles);
		return generateToken(map,details);
	}
	
	public String generateToken(Map<String, Object> extraClaims,UserDto details) {
		return Jwts
				.builder()
				.addClaims(extraClaims)
				.setSubject(details.getEmail())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24 *7))
				.signWith(getSignInKey())
				.compact();
	}
	
	public boolean isTokenValid(String token) {						
		return !isTokenExpired(token);
	}

	public boolean isTokenExpired(String token) {
		try {
			return extractClaim(token, Claims::getExpiration).before(new Date());
		} catch (MalformedJwtException ex) {
			logger.error("Invalid Jwt Token");
		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token");
		}  catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty.");
		}  catch(Exception e) {
			e.printStackTrace();
			logger.error("Invalid JWT token");
		}
		return false;
	}	
	
	public void validateToken(final String token) {
		extractAllClaims(token);
	}
}
