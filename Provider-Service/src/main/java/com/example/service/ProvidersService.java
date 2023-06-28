package com.example.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.dto.TokenDataDto;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ProvidersService {
		
	public ResponseEntity<TokenDataDto> fetchUserDetails(HttpServletRequest request){				
		TokenDataDto data = new TokenDataDto();
		data.setProviderName(request.getHeader("provider_name"));
		data.setProviderCode(request.getHeader("provider_code"));
		data.setUsername(request.getHeader("username"));
		data.setEmail(request.getHeader("email"));		
		data.setRoles(request.getHeader("roles"));
		return ResponseEntity.ok(data);
	}
}
