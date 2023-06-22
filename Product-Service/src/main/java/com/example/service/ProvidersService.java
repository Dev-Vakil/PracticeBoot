package com.example.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.entities.Providers;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ProvidersService {
	
	@Autowired
	RestTemplate restTemplate;
	
	final String ROOT_URI = "http://localhost:8081/providers/current-user";
		
	public ResponseEntity<Providers> fetchUserDetails(HttpServletRequest request){
		
		String token = request.getHeader("Authorization");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		headers.set("Authorization", token);
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);		
		
//		ResponseEntity<Providers> response = restTemplate.postForEntity(ROOT_URI, entity, Providers.class);
		ResponseEntity<Providers> response = restTemplate.exchange(ROOT_URI, HttpMethod.GET, entity, Providers.class);
		return response;
	}
}
