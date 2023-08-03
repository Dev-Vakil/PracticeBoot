package com.example.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.savePayerlistDto;
import com.example.entities.Pricelist;
import com.example.service.AvailableServicePricelistService;
import com.example.service.PricelistService;

@RequestMapping("/provider")
@RestController
@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class PricelistController {

	@Autowired
	private AvailableServicePricelistService availableServicePricelistService;
	
	@Autowired
	private PricelistService pricelistService;
	
	@GetMapping("service/download")
	public ResponseEntity<Boolean> downloadAvailablePricelistService(@RequestParam("payerId") Integer payerId) throws IOException{
		
		Boolean result = availableServicePricelistService.downloadAvailablePricelistService(payerId);

		return ResponseEntity.ok(result);
	}
		
	@PostMapping("service/upload")
	public ResponseEntity<Boolean> uploadNewServicePricelist(@RequestParam("file") MultipartFile file) throws IOException{		
		Boolean result = availableServicePricelistService.uploadPricelistService(file,1);
		return ResponseEntity.ok(result);
	}			

	@GetMapping("pricelist")
	public ResponseEntity<?> getAllPricelist(){
		List<Pricelist> pricelist = pricelistService.getAllPriceList();
		return ResponseEntity.ok(pricelist);
	}
}

