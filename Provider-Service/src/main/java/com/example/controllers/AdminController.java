package com.example.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.PayerProviderDto;
import com.example.dto.TokenDataDto;
import com.example.entities.Payer;
import com.example.entities.PayerProvider;
import com.example.entities.Providers;
import com.example.service.PayerProviderService;
import com.example.service.PayerService;
import com.example.service.ProvidersService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("admin/provider")
public class AdminController {
	
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
	public ResponseEntity<List<Providers>> getProviders(@RequestParam("providerFilter") Optional<String> providerFilter){
		String filterString = providerFilter.orElse("");	
		return providersService.getProviders(filterString);
	}
	
	@GetMapping("/payers")
	public ResponseEntity<List<Payer>> getPayers(@RequestParam("payerFilter") Optional<String> payerFilter){			
		String filterString = payerFilter.orElse("");
		return payerService.getPayers(filterString);
	}
	
	@PostMapping("/payerProvider")
	public ResponseEntity<Boolean> savePayerProvider(@RequestBody PayerProviderDto payerProviderDto){
		return payerProviderService.save(payerProviderDto);
	}
	
	@GetMapping("/payerProvider")
	public ResponseEntity<List<PayerProvider>> getPayerProviders(){
		return payerProviderService.getAll();		
	}
	
	@GetMapping("/payerProviderStatus")
	public ResponseEntity<Boolean> getPayerProviderStatus(@RequestParam(name = "providerId") Integer providerId, @RequestParam(name = "payerId") Integer payerId){
		return payerProviderService.getPayerProviderStatus(providerId,payerId);
	}
	
	
}
