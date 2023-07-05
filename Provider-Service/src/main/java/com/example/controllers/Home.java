package com.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.PayerProviderDto;
import com.example.dto.TokenDataDto;
import com.example.entities.Payer;
import com.example.entities.PayerProvider;
import com.example.entities.Providers;
import com.example.repository.PayerRepository;
import com.example.service.PayerProviderService;
import com.example.service.PayerService;
import com.example.service.ProvidersService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Response;

@RestController
@RequestMapping("/provider")
public class Home {
	
	@Autowired
	private ProvidersService providersService;
	
	@Autowired
	private PayerService payerService;
	
	@Autowired
	private PayerProviderService payerProviderService;
	
	@GetMapping("/current-user")
	public ResponseEntity<TokenDataDto> fetchUserDetails(HttpServletRequest request) {
		return providersService.fetchUserDetails(request);
	}
		
	@GetMapping("/providers")
	public ResponseEntity<List<Providers>> getProviders(){
		return providersService.getProviders();
	}
	
	@GetMapping("/payers")
	public ResponseEntity<List<Payer>> getPayers(){
		return payerService.getPayers();
	}
	
	@PostMapping("/payerProvider")
	public ResponseEntity<Boolean> savePayerProvider(@RequestBody PayerProviderDto payerProviderDto){
		return payerProviderService.save(payerProviderDto);
	}
	
	@GetMapping("/payerProvider")
	public ResponseEntity<List<PayerProvider>> getPayerProviders(){
		return payerProviderService.getAll();		
	}
}
