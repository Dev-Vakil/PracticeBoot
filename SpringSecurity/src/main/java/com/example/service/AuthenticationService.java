package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PayerRepository payerRepository;
	
	@Autowired
	private JwtService jwtService;

	public AuthResponseDto registerProvider(UserDto details){
		try {
			
			Providers provider = Providers.builder()
					.providerCode(details.getCode())
					.providerName(details.getName())
					.username(details.getUsername())
					.email(details.getEmail())					
					.isActive(true)
					.password(new BCryptPasswordEncoder().encode(details.getPassword()))					
					.build();			
			Providers save = providersRepository.save(provider);			
			RoleAssociation roleAssociation = new RoleAssociation();
			roleAssociation.setProvider(provider);
			roleAssociation.setPayer(null);
			Role role = roleRepository.findByName("USER")
					.orElseThrow(()-> new UsernameNotFoundException("user role not found"));
			roleAssociation.setRole(role);
			roleAssociationRepository.save(roleAssociation);
			
			AuthResponseDto response = new AuthResponseDto();		
			
			List<RoleAssociation> roles = roleAssociationRepository.findByProvider(provider);
			
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
			Payer payer = Payer.builder()
					.payerName(details.getName())
					.payerCode(details.getCode())
					.email(details.getEmail())
					.isActive(true)
					.password(new BCryptPasswordEncoder().encode(details.getPassword()))					
					.build();			
			payerRepository.save(payer);			
			Payer newPayers = payerRepository.findByEmail(payer.getEmail())
					.orElseThrow(()-> new UsernameNotFoundException("user not found"));			
			
			RoleAssociation roleAssociation = new RoleAssociation();
			roleAssociation.setPayer(newPayers);			
			Role role = roleRepository.findByName("PAYER")
					.orElseThrow(()-> new UsernameNotFoundException("user role not found"));
			roleAssociation.setRole(role);
			roleAssociationRepository.save(roleAssociation);
			
			AuthResponseDto response = new AuthResponseDto();		
			
			List<RoleAssociation> roles = roleAssociationRepository.findByPayer(newPayers);
			
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
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							details.getEmail(),
							details.getPassword()
					)
				);
				UserDto userDto = new UserDto(); 
				List<RoleAssociation> roles = new ArrayList<>(); 
				if(details.getRole().equals("PROVIDER")) {
					var user = providersRepository.findByEmail(details.getEmail()).orElseThrow();				
					roles = roleAssociationRepository.findByProvider(user);
					userDto.setName(user.getProviderName());
					userDto.setCode(user.getProviderCode());
					userDto.setUsername(user.getUsername());
					userDto.setEmail(user.getEmail());
				}
				else {
					var user = payerRepository.findByEmail(details.getEmail()).orElseThrow();
					roles = roleAssociationRepository.findByPayer(user);
					userDto.setName(user.getPayerName());
					userDto.setCode(user.getPayerCode());
					userDto.setEmail(user.getEmail());
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
