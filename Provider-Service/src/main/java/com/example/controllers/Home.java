package com.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.TokenDataDto;
import com.example.entities.Providers;
import com.example.service.ProvidersService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Response;

@RestController
@RequestMapping("/provider")
public class Home {
	
	@Autowired
	private ProvidersService providersService;
	
	@GetMapping("/current-user")
	public ResponseEntity<TokenDataDto> fetchUserDetails(HttpServletRequest request) {
		return providersService.fetchUserDetails(request);
	}
		
	@GetMapping("/providers")
	public ResponseEntity<List<Providers>> getProviders(){
		return providersService.getProviders();
	}
}
