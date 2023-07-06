package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.dto.PayerFilterDto;
import com.example.entities.Payer;
import com.example.repository.PayerRepository;

@Service
public class PayerService {
	
	@Autowired
	private PayerRepository payerRepository;

	public ResponseEntity<List<Payer>> getPayers(String payerFilter) {
		try {			
			return ResponseEntity.ok(payerRepository.findAllPayers(payerFilter)
					.orElseThrow(()-> new NullPointerException("Payer Not found")));
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
	
}
