package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("isAuthenticated()")
public class demoController {

	@GetMapping("/hello")
	public ResponseEntity<?> hello(){
		return ResponseEntity.ok("hello");
	}
}
