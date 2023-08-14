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

import com.example.entities.ServicePricelist;
import com.example.service.ServicePricelistService;

@RequestMapping("/servicePricelist")
@RestController
@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class ServicePricelistController {
	
	@Autowired
	private ServicePricelistService servicePricelistService; 
	
	@GetMapping("/")	
	public ResponseEntity<?> viewPricelistPage(@RequestParam("page") Integer page,@RequestParam("size") Integer size,@RequestParam("pricelistId") Integer pricelistId,@RequestParam("status") String status){
		Pageable pageable = PageRequest.of(page, size);								
		Page<ServicePricelist> servicePricelist = servicePricelistService.getServicePricelistPage(pageable, pricelistId, status);
		if(servicePricelist.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("No Services Found");
		return ResponseEntity.ok(servicePricelist);
	}		
}
