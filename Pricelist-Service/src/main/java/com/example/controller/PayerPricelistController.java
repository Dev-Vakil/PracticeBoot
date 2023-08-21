package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Pricelist;
import com.example.entities.ServicePricelist;
import com.example.service.PricelistService;
import com.example.service.ServicePricelistService;

@RequestMapping("payer/pricelist")
@RestController
@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class PayerPricelistController {
	
	@Autowired
	private PricelistService pricelistService;
	
	@Autowired
	private ServicePricelistService servicePricelistService; 
	
	@GetMapping("/")	
	public ResponseEntity<Page<Pricelist>> viewPricelistPage(@RequestParam("page") Integer page,@RequestParam("size") Integer size, @RequestParam("payerId") Integer payerId){
		Pageable pageable = PageRequest.of(page, size);
		Page<Pricelist> pricelist = pricelistService.getPricelistByPayerIdPage(pageable, payerId);		
		if(pricelist.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		return ResponseEntity.ok(pricelist);		
	}
	
	@GetMapping("/service")	
	public ResponseEntity<?> viewPricelistPage(@RequestParam("page") Integer page,@RequestParam("size") Integer size,@RequestParam("pricelistId") Integer pricelistId,@RequestParam("status") String status){
		Pageable pageable = PageRequest.of(page, size);								
		Page<ServicePricelist> servicePricelist = servicePricelistService.getServicePricelistPage(pageable, pricelistId, status);
		if(servicePricelist.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("No Services Found");
		return ResponseEntity.ok(servicePricelist);
	}
}
