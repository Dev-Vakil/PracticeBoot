package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.config.JwtService;
import com.example.dto.ProviderDto;
import com.example.dto.AuthResponseDto;
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

	public AuthResponseDto register(ProviderDto details) {
		try {
			Providers provider = Providers.builder()
					.provider_code(details.getProvider_code())
					.provider_name(details.getProvider_name())
					.username(details.getUsername())
					.email(details.getEmail())
					.is_active(true)
					.password(new BCryptPasswordEncoder().encode(details.getPassword()))					
					.build();
			providersRepository.save(provider);
			AuthResponseDto response = new AuthResponseDto();
			String token = jwtService.generateToken(provider);		
			response.setToken(token);
			return response;
		}		
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
							
	}

	public AuthResponseDto login(ProviderDto details) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							details.getEmail(),
							details.getPassword()
					)
				);
				var user = providersRepository.findByEmail(details.getEmail()).orElseThrow();
				String token = jwtService.generateToken(user);
				AuthResponseDto response = new AuthResponseDto();
				response.setToken(token);
				return response;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
