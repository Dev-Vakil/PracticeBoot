package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.AuthResponseDto;
import com.example.dto.ProviderDto;
import com.example.entities.Providers;
import com.example.service.AuthenticationService;
import com.example.service.ProvidersService;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService service;
	
	@Autowired
	private ProvidersService providersService;
	
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody ProviderDto provider){
		AuthResponseDto result =  service.register(provider);		
		if(result != null) {
			return ResponseEntity.ok(result);
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody ProviderDto provider){
		try {					
			AuthResponseDto result = service.login(provider);
			if(result != null) {
				return ResponseEntity.ok(result);
			}
			else {
				return ResponseEntity.ok("Invalid Credentials");
			}
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@GetMapping("/validate")
	public Boolean validateToken(@RequestParam String token) {
		try {
			service.validateToken(token);
			return true;			
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	@GetMapping("/current-user")
	public ResponseEntity<?> userDetailsFromToken(HttpServletRequest request){		
		try {				
			ProviderDto providerDto = (ProviderDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();					
			return ResponseEntity.ok(providerDto);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}
	}
	
	@PostMapping("/findProviderCode")
	public ResponseEntity<?> findProviderCode(@RequestBody String provider_code){		
		try {			
			Boolean result = providersService.findProviderCode(provider_code);
			return ResponseEntity.ok(result);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(false);
		}
	}
	
	@PostMapping("/findEmail")
	public ResponseEntity<?> findEmail(@RequestBody String email){
		try {
			Providers result = providersService.loadUserByUsername(email);
			return ResponseEntity.ok(result);
		}
		catch(UsernameNotFoundException e) {
			return ResponseEntity.ok(null);
		}
		catch(Exception e) {
			return ResponseEntity.ok(null);	
		}
	}
}
