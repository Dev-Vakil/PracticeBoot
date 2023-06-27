package com.example.config;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.dto.ProviderDto;
import com.example.entities.Providers;
import com.example.entities.Providers.Role;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {
		
//		final String authHeader = request.getHeader("Authorization");
//		final String jwt;
//		final String userEmail;		
//		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
//			filterChain.doFilter(request, response);
//			return;
//		}		
//		jwt = authHeader.substring(7);
//		userEmail =  jwtService.extractEmail(jwt);		
//		if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//			Providers providers = (Providers) this.userDetailsService.loadUserByUsername(userEmail);
//			if(jwtService.isTokenValid(jwt, providers)) {	
//				
//				Claims claims = jwtService.extractAllClaims(jwt);
//				ProviderDto providerDto = new ProviderDto();
//				providerDto.setEmail(claims.get("provider_name").toString());
//				providerDto.setProvider_name(claims.get("provider_name").toString());
//				providerDto.setProvider_code(claims.get("provider_code").toString());
//				providerDto.setUsername(claims.get("username").toString());
//				providerDto.setEmail(claims.get("email").toString());
//				providerDto.setPassword("********");
//				
//				Collection<? extends GrantedAuthority> authorities =List.of(new SimpleGrantedAuthority(Role.USER.name()));
//				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(providerDto,null,authorities);
//				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));				
//				SecurityContextHolder.getContext().setAuthentication(authToken);
//			}else {
//				System.out.println("Not Validated");
//			}
//		}
		filterChain.doFilter(request, response);
	}

}
