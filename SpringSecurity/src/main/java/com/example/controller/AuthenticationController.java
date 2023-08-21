package com.example.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
@CrossOrigin
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService service;
	
	@Autowired
	private ProvidersService providersService;
	
	@Autowired
	private PayerService payerService;
	
	@GetMapping("/validateUser")
	public Principal user(Principal user) {
		return user;
	}
	
	@PostMapping("/register")
	public ResponseEntity<AuthResponseDto> registerProvider(@RequestBody UserDto user){
		AuthResponseDto result;
		if(user.getUserType().equals("PAYER")) {			
			result =  service.registerPayer(user);					
		}
		else {
			result =  service.registerProvider(user);
		}
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

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/check_token")
	public ResponseEntity<Boolean> userDetails(){		
		try {
			return ResponseEntity.ok(true);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/current-user")	
	public ResponseEntity<UsernamePasswordAuthenticationToken> currentUser(HttpServletRequest request,UsernamePasswordAuthenticationToken token){		
		try {							
			return ResponseEntity.ok(token);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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
	
	@PostMapping("/findEmail")
	public ResponseEntity<Boolean> findPayerEmail(@RequestBody String email){
		Boolean result1 = true;
		try {			
			result1 = payerService.findByEmail(email);
			Providers providers = providersService.loadUserByUsername(email);
			return ResponseEntity.ok(result1 || (providers != null));
		}
		catch(UsernameNotFoundException e) {
			return ResponseEntity.ok(result1);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(true);
		}
	}	
}
