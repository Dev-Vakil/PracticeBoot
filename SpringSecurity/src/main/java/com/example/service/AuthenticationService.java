package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.config.JwtService;
import com.example.dto.ProviderDto;
import com.example.entities.Providers;
import com.example.repository.ProvidersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	@Autowired
	private ProvidersRepository providersRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;

	public String register(ProviderDto details) {
		try {
			Providers provider = Providers.builder()
					.username(details.getUsername())
					.email(details.getEmail())
					.password(new BCryptPasswordEncoder().encode(details.getPassword()))
					.build();
			providersRepository.save(provider);
			var token = jwtService.generateToken(provider);		
			return token;
		}		
		catch(Exception e) {
			return "User Not Registered";
		}
							
	}

	public String login(ProviderDto details) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							details.getEmail(),
							details.getPassword()
					)
				);
				var user = providersRepository.findByEmail(details.getEmail()).orElseThrow();
				var token = jwtService.generateToken(user);		
				return token;
		}
		catch(Exception e) {
			return "Invalid User Credentials";
		}
	}
	
	
}
