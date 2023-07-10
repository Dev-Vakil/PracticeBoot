package com.example.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.TokenDataDto;
import com.example.entities.Payer;
import com.example.service.PayerProviderService;
import com.example.service.ProvidersService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("user/provider")
public class UserController {
	
	@Autowired
	private ProvidersService providersService;
	
	@Autowired
	private PayerProviderService payerProviderService;
	
	@GetMapping("/current-user")
	public ResponseEntity<TokenDataDto> fetchUserDetails(HttpServletRequest request) {
		return providersService.fetchUserDetails(request);
	}
	
	@GetMapping("/payers")
	public ResponseEntity<List<Payer>> associatedPayers(@RequestParam("email") String email, @RequestParam("search") Optional<String> search){
		String searchFilter = search.orElse("");	
		return payerProviderService.associatedPayers(email, searchFilter);
	}
}
