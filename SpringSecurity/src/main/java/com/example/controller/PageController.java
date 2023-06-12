package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pages")
public class PageController {
	
	@GetMapping("/")
	public ResponseEntity<String> hello(){
		return ResponseEntity.ok("Hello, Welcome to the website.");
	}
}
