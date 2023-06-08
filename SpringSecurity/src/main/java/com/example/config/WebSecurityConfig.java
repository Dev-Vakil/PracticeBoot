package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	public SecurityFilterChain filterchain(HttpSecurity http) throws Exception{
		
		http.csrf().disable().authorizeHttpRequests()
		.requestMatchers("/").permitAll()
		.anyRequest().authenticated()
		.and();
		
		return http.build();	
	}
}
