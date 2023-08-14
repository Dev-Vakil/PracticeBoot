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
import com.example.service.PricelistService;

@RequestMapping("/pricelist")
@RestController
@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class PricelistController {
	
	@Autowired
	private PricelistService pricelistService;
	
	@GetMapping("/")	
	public ResponseEntity<Page<Pricelist>> viewPricelistPage(@RequestParam("page") Integer page,@RequestParam("size") Integer size){
		Pageable pageable = PageRequest.of(page, size);
		Page<Pricelist> pricelist = pricelistService.getPricelistPage(pageable);
		if(pricelist.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		return ResponseEntity.ok(pricelist);		
	}
	
}
