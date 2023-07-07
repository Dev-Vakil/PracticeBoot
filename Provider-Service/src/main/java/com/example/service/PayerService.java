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
			return ResponseEntity.ok(payerRepository.findAll(hasRolePayer().and(hasPayerName(payerFilter).or(hasEmail(payerFilter)))));					
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
	
	static Specification<Payer> hasPayerName(String name) {
	    return (payer, cq, cb) -> cb.like(payer.get("payerName"), "%" + name + "%");	    
	}
	
	static Specification<Payer> hasEmail(String email) {
	    return (payer, cq, cb) -> cb.like(payer.get("email"), "%" + email + "%");
	}
	
	static Specification<Payer> hasRolePayer() {
	    return (payer, cq, cb) -> cb.equal(payer.get("roleAssociation").get("role").get("id"), 3);
	}
	
}
