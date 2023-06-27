package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.config.JwtService;
import com.example.dto.ProviderDto;
import com.example.dto.AuthResponseDto;
import com.example.entities.Providers;
import com.example.entities.Role;
import com.example.entities.RoleAssociation;
import com.example.repository.ProvidersRepository;
import com.example.repository.RoleAssociationRepository;
import com.example.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	@Autowired	
	private RoleRepository roleRepository;
	
	@Autowired
	private RoleAssociationRepository roleAssociationRepository;
	
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
			Providers providers = providersRepository.findByEmail(provider.getEmail())
					.orElseThrow(()-> new UsernameNotFoundException("user not found"));			
			
			RoleAssociation roleAssociation = new RoleAssociation();
			roleAssociation.setProvider(providers);			
			Role role = roleRepository.findByName("USER")
					.orElseThrow(()-> new UsernameNotFoundException("user role not found"));
			roleAssociation.setRole(role);
			roleAssociationRepository.save(roleAssociation);
			
			AuthResponseDto response = new AuthResponseDto();		
			
			List<RoleAssociation> roles = roleAssociationRepository.findByProvider(providers);
			
			String token = jwtService.generateToken(providers,roles);
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
				
				List<RoleAssociation> roles = roleAssociationRepository.findByProvider(user);
				
				String token = jwtService.generateToken(user,roles);
				AuthResponseDto response = new AuthResponseDto();
				response.setToken(token);
				return response;
		}
		catch(BadCredentialsException e) {			
			return null;
		}
		catch(Exception e) {			
			throw e;
		}
	}
	
	public void validateToken(String token) {
		jwtService.validateToken(token);
	}
}
