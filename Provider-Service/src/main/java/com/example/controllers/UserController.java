package com.example.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Payer;
import com.example.service.PayerProviderService;


@RequestMapping("/user")
@PreAuthorize("isAuthenticated()")
@RestController
@CrossOrigin()
public class UserController {
	
	@Autowired
	private PayerProviderService payerProviderService;	
	
	@GetMapping("/payers")
	public ResponseEntity<List<Payer>> associatedPayers(@RequestParam("email") String email, @RequestParam("search") Optional<String> search){
		String searchFilter = search.orElse("");	
		return payerProviderService.associatedPayers(email, searchFilter);
	}
}
