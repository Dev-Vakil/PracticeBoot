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

	@Bean
	public SecurityFilterChain filterchain(HttpSecurity http) throws Exception{
		
        http
       		.csrf(csrf -> csrf.disable()).cors(cors->cors.disable())
       		.authorizeHttpRequests(authz -> authz       		        
	       		        .anyRequest().permitAll()	
	       		)
       		.sessionManagement(management -> management
	   		        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	   		)  	
			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        		
		return http.build();	
	}
}
