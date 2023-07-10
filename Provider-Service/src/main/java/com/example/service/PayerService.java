package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entities.Payer;
import com.example.repository.PayerRepository;

@Service
public class PayerService {
	
	@Autowired
	private PayerRepository payerRepository;

	public ResponseEntity<List<Payer>> getPayers(String payerFilter) {
		try {									
			return ResponseEntity.ok(payerRepository.findAll(hasRolePayer().and(hasFilter(payerFilter))));					
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
	
	static Specification<Payer> hasFilter(String filter) {
		return (payer, cq, cb) -> cb.or(cb.like(payer.get("payerName"), "%" + filter + "%"),cb.like(payer.get("email"), "%" + filter + "%"));
	}	
	
	static Specification<Payer> hasRolePayer() {
	    return (payer, cq, cb) -> cb.equal(payer.get("roleAssociation").get("role").get("id"), 3);
	}
	
}
