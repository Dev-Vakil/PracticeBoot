package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthFilter;
	
	@Autowired
	private AuthenticationProvider authenticationProvider;

	@Bean
	public SecurityFilterChain filterchain(HttpSecurity http) throws Exception{
        http
       		.csrf(csrf -> csrf.disable())
       		.authorizeHttpRequests(authz -> authz
       		        .requestMatchers("/**").permitAll()
	       		        .anyRequest().authenticated()
	       		)
       		.sessionManagement(management -> management
	   		        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	   		)
       		.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);		
		
		return http.build();	
	}
}
