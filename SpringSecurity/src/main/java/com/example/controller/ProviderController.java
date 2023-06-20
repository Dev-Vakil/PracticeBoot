package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.config.JwtService;
import com.example.dto.ProviderDto;
import com.example.entities.Providers;
import com.example.service.ProvidersService;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/providers")
@RestController
@CrossOrigin
public class ProviderController {	
	
	@Autowired
	ProvidersService providersService;
	
//	@Autowired
//	private JwtService jwtService;
	
	@PostMapping("/fetchUserDetails")
	public ResponseEntity<?> userDetailsFromToken(){	
		try {	
			Providers providers = (Providers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			ProviderDto result = new ProviderDto(providers.getProvider_name(),providers.getProvider_code(),providers.getUsername(),providers.getEmail(),null);						
			return ResponseEntity.ok(result);					
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
	
//	@PostMapping("/fetchUserDetails")
//	public ResponseEntity<?> userDetailsFromToken2(HttpServletRequest request){	
//		try {	
//			final String authHeader = request.getHeader("Authorization");
//			if(authHeader == null || !authHeader.startsWith("Bearer ")) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//			String token = authHeader.substring(7);
//			String userEmail =  jwtService.extractEmail(token);
//			Providers providers = (Providers) this.providersService.loadUserByUsername(userEmail);
//			if(jwtService.isTokenValid(token, providers)){
//				ProviderDto result = new ProviderDto(providers.getProvider_name(),providers.getProvider_code(),providers.getUsername(),providers.getEmail(),null);						
//				return ResponseEntity.ok(result);							
//			}
//			else {
//				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//			}
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//	}
	
}
