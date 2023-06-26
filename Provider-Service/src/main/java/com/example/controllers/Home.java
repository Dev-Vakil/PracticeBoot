package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.TokenDataDto;
import com.example.entities.Providers;
import com.example.service.ProvidersService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/provider")
public class Home {
	
	@Autowired
	private ProvidersService providersService;
	
	@GetMapping("/current-user")
	public ResponseEntity<TokenDataDto> fetchUserDetails(HttpServletRequest request) {
		return providersService.fetchUserDetails(request);
	}
		
	
}
