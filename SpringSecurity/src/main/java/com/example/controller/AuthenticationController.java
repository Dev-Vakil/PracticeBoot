package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ProviderDto;
import com.example.service.AuthenticationService;


@RequestMapping("/providers")
@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService service;
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody ProviderDto provider){				
		return ResponseEntity.ok(service.register(provider));		
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody ProviderDto provider){
		return ResponseEntity.ok(service.login(provider));
	}
}
