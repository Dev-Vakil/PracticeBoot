package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.AuthResponseDto;
import com.example.dto.ProviderDto;
import com.example.service.AuthenticationService;
import com.example.service.ProvidersService;

@RequestMapping("/auth")
@RestController
@CrossOrigin
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService service;
	
	@Autowired
	private ProvidersService providersService;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody ProviderDto provider){
		AuthResponseDto result = new AuthResponseDto();  
		result = service.register(provider);		
		if(result != null) {
			return ResponseEntity.ok("Registered Successfully");
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody ProviderDto provider){
		AuthResponseDto result = new AuthResponseDto();  
		result = service.login(provider);
		if(result != null) {
			return ResponseEntity.ok("LogedIn Successfully");
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@PostMapping("/findProviderCode")
	public ResponseEntity<?> findProviderCode(String provider_code){
		Boolean result = providersService.findProviderCode(provider_code);
		return ResponseEntity.ok(result);
	}
}
