package com.example.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		OAuth2Authentication auth =  (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> map = (Map<String, Object>) auth.getUserAuthentication().getDetails();
		
		ArrayList<Map<String,String>> roles =  (ArrayList<Map<String, String>>) map.get("authorities");
		
		Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(roles.get(0).get("authority")));
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(map, null, authorities));
		
		filterChain.doFilter(request,response);
	}
	
}
