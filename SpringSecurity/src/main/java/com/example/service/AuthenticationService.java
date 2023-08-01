package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.config.JwtService;
import com.example.dto.UserDto;
import com.example.dto.AuthResponseDto;
import com.example.dto.LoginDto;
import com.example.entities.Payer;
import com.example.entities.Providers;
import com.example.entities.Role;
import com.example.entities.RoleAssociation;
import com.example.repository.PayerRepository;
import com.example.repository.ProvidersRepository;
import com.example.repository.RoleAssociationRepository;
import com.example.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService{
	
	@Autowired	
	private RoleRepository roleRepository;
	
	@Autowired
	private RoleAssociationRepository roleAssociationRepository;
	
	@Autowired
	private ProvidersRepository providersRepository;
	
	@Autowired
	private PayerRepository payerRepository;
	
	@Autowired
	private JwtService jwtService;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


	public AuthResponseDto registerProvider(UserDto details){
		try {
			//save providers
			Providers provider = Providers.builder()
					.providerCode(details.getCode())
					.providerName(details.getName())
					.username(details.getUsername())
					.email(details.getEmail())					
					.isActive(true)
					.password(new BCryptPasswordEncoder().encode(details.getPassword()))					
					.build();				
			Providers savedProvider = providersRepository.save(provider);
			
			//save role association for providers
			RoleAssociation roleAssociation = new RoleAssociation();
			roleAssociation.setProvider(savedProvider);
			roleAssociation.setPayer(null);
			Role role = roleRepository.findByName("USER")
					.orElseThrow(()-> new UsernameNotFoundException("user role not found"));
			roleAssociation.setRole(role);
			roleAssociationRepository.save(roleAssociation);
			
			AuthResponseDto response = new AuthResponseDto();								
			List<RoleAssociation> roles = roleAssociationRepository.findByProvider(savedProvider.getProviderId());
			
			//generating and sending token via response
			String token = jwtService.generateToken(details,roles);
			response.setToken(token);
			List<String>roleNames = new ArrayList<>();
			for(RoleAssociation r : roles) {
				roleNames.add(r.getRole().getName());
			}
			response.setRoles(roleNames);
			return response;
		}		
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
							
	}

	public AuthResponseDto registerPayer(UserDto details) {
		try {
			//save payer
			Payer payer = Payer.builder()
					.payerName(details.getName())
					.payerCode(details.getCode())
					.email(details.getEmail())
					.isActive(true)
					.password(new BCryptPasswordEncoder().encode(details.getPassword()))					
					.build();			
			Payer savedPayers = payerRepository.save(payer);									
			
			//save role association for providers
			RoleAssociation roleAssociation = new RoleAssociation();
			roleAssociation.setPayer(savedPayers);			
			Role role = roleRepository.findByName("PAYER")
					.orElseThrow(()-> new UsernameNotFoundException("user role not found"));
			roleAssociation.setRole(role);
			roleAssociationRepository.save(roleAssociation);
			
			AuthResponseDto response = new AuthResponseDto();					
			List<RoleAssociation> roles = roleAssociationRepository.findByPayer(savedPayers.getPayerId());
			
			//generating and sending token via response
			String token = jwtService.generateToken(details,roles);
			response.setToken(token);
			List<String>roleNames = new ArrayList<>();
			for(RoleAssociation r : roles) {
				roleNames.add(r.getRole().getName());
			}
			response.setRoles(roleNames);
			return response;
		}		
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public AuthResponseDto login(LoginDto details) {
		try {							
				UserDto userDto = new UserDto(); 
				List<RoleAssociation> roles;						
				var payer = payerRepository.findByEmail(details.getEmail()).orElse(null);
				if(payer == null) {
					var provider = providersRepository.findByEmail(details.getEmail()).orElseThrow();	
					System.out.println(provider.getProviderCode());
					if(!encoder.matches(details.getPassword(),provider.getPassword()))
						throw new UsernameNotFoundException(null);
					roles = roleAssociationRepository.findByProvider(provider.getProviderId());
					if(roles.isEmpty())
						roles = roleAssociationRepository.findByAdmin(provider.getProviderId());
					userDto.setName(provider.getProviderName());
					userDto.setCode(provider.getProviderCode());
					userDto.setUsername(provider.getUsername());
					userDto.setEmail(provider.getEmail());
				}
				else {					
					if(!encoder.matches(details.getPassword(),payer.getPassword()))
						throw new UsernameNotFoundException(null);
					roles = roleAssociationRepository.findByPayer(payer.getPayerId());
					userDto.setName(payer.getPayerName());
					userDto.setCode(payer.getPayerCode());
					userDto.setEmail(payer.getEmail());
				}
				String token = jwtService.generateToken(userDto,roles);				
				AuthResponseDto response = new AuthResponseDto();
				response.setToken(token);
				List<String>roleNames = new ArrayList<>();
				for(RoleAssociation role : roles) {
					roleNames.add(role.getRole().getName());
				}
				response.setRoles(roleNames);
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
