package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Providers;
import com.example.service.ProvidersService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class Home {
	
	@Autowired
	private ProvidersService providersService;
	
	@GetMapping("/current-user")
	public ResponseEntity<Providers> fetchUserDetails(HttpServletRequest request) {
		return providersService.fetchUserDetails(request);
	}
		
	
}
