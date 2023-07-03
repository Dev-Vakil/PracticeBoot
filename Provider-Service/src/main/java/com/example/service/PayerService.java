package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entities.Payer;
import com.example.repository.PayerRepository;

@Service
public class PayerService {
	
	@Autowired
	private PayerRepository payerRepository;

	public ResponseEntity<List<Payer>> getPayers() {
		try {
			return ResponseEntity.ok(payerRepository.findAllPayers()
					.orElseThrow(()-> new NullPointerException("Provider Not found")));
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
	
}
