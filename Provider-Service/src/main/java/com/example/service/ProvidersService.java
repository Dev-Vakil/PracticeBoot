package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.dto.TokenDataDto;
import com.example.entities.Providers;
import com.example.repository.ProvidersRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ProvidersService {
	
	@Autowired
	private ProvidersRepository providerRepository;
	
	public ResponseEntity<TokenDataDto> fetchUserDetails(HttpServletRequest request){				
		TokenDataDto data = new TokenDataDto();
		data.setProviderName(request.getHeader("providerName"));
		data.setProviderCode(request.getHeader("providerNode"));
		data.setUsername(request.getHeader("username"));
		data.setEmail(request.getHeader("email"));		
		data.setRoles(request.getHeader("roles"));
		return ResponseEntity.ok(data);
	}
	
	public ResponseEntity<List<Providers>> getProviders(){
		try {
//			return ResponseEntity.ok(providerRepository.findAll());
			return ResponseEntity.ok(providerRepository.findAllProviders()
					.orElseThrow(()-> new NullPointerException("Provider Not found")));
		}catch(Exception e) {			
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
}
