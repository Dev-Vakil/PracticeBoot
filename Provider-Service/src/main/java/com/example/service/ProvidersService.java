package com.example.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.dto.TokenDataDto;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ProvidersService {
		
	public ResponseEntity<TokenDataDto> fetchUserDetails(HttpServletRequest request){				
		TokenDataDto data = new TokenDataDto();
		data.setProvider_name(request.getHeader("provider_name"));
		data.setProvider_code(request.getHeader("provider_code"));
		data.setUsername(request.getHeader("username"));
		data.setEmail(request.getHeader("email"));		
		return ResponseEntity.ok(data);
	}
}
