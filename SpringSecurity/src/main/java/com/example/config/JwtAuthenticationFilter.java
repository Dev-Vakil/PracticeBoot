package com.example.config;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.dto.UserDto;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
		
	@Autowired
	private JwtService jwtService;
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String userEmail;	
			
		if(authHeader == null || !authHeader.startsWith("Bearer ")) { 
			filterChain.doFilter(request, response);
			return;
		}
		
		jwt = authHeader.substring(7);
		
		userEmail =  jwtService.extractEmail(jwt);
		if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {		
			if(jwtService.isTokenValid(jwt)) {				
				Claims claims = jwtService.extractAllClaims(jwt);			
				UserDto userDto = new UserDto();		
				userDto.setId((Integer) claims.get("id"));
				userDto.setName(claims.get("name").toString());
				userDto.setCode(claims.get("code").toString());
				userDto.setUsername(claims.get("username").toString());
				userDto.setEmail(claims.get("email").toString());
				userDto.setPassword("********");								
				
				List<String> roles = (List<String>) claims.get("roles");
				Collection<? extends GrantedAuthority> authorities =List.of(new SimpleGrantedAuthority("ROLE_"+roles.get(0).toString()));
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDto,null,authorities);
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));	
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}else {
				logger.error("Not Validated");
			}
		}	
		filterChain.doFilter(request, response);
	}
}
