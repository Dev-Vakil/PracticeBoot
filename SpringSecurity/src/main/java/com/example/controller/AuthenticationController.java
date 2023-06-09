package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.AuthResponseDto;
import com.example.dto.LoginDto;
import com.example.dto.UserDto;
import com.example.entities.Providers;
import com.example.service.AuthenticationService;
import com.example.service.PayerService;
import com.example.service.ProvidersService;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService service;
	
	@Autowired
	private ProvidersService providersService;
	
	@Autowired
	private PayerService payerService;	
	
	
	@PostMapping("/provider/register")
	public ResponseEntity<AuthResponseDto> registerProvider(@RequestBody UserDto provider){
		
		AuthResponseDto result =  service.registerProvider(provider);			
		if(result != null) {
			return ResponseEntity.ok(result);
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@PostMapping("/payer/register")
	public ResponseEntity<AuthResponseDto> registerPayer(@RequestBody UserDto payer){
		
		AuthResponseDto result =  service.registerPayer(payer);			
		if(result != null) {
			return ResponseEntity.ok(result);
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDto details){
		try {								
			AuthResponseDto result = service.login(details);
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
	public ResponseEntity<UserDto> userDetailsFromToken(HttpServletRequest request){		
		try {				
			UserDto data = new UserDto();
			data.setName(request.getHeader("name"));
			data.setCode(request.getHeader("code"));
			data.setUsername(request.getHeader("username"));
			data.setEmail(request.getHeader("email"));					
			return ResponseEntity.ok(data);				
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
	
	@PostMapping("/findPayerCode")
	public ResponseEntity<Boolean> findPayerCode(@RequestBody String payer_code){		
		try {			
			Boolean result = payerService.findByPayerCode(payer_code);
			return ResponseEntity.ok(result);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(false);
		}
	}
	
	@PostMapping("/findPayerEmail")
	public ResponseEntity<Boolean> findPayerEmail(@RequestBody String email){
		try {			
			Boolean result = payerService.findByEmail(email);
			return ResponseEntity.ok(result);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(false);
		}
	}
	
	@PostMapping("/findProviderEmail")
	public ResponseEntity<Providers> findProviderEmail(@RequestBody String email){
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
