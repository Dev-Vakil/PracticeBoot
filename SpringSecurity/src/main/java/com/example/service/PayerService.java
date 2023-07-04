package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entities.Payer;
import com.example.repository.PayerRepository;

@Service
public class PayerService {
	
	@Autowired
	private PayerRepository payerRepository;

	public Boolean findByEmail(String email) {
		
		Payer payer = payerRepository.findByEmail(email).orElse(null);
		try {
			if(payer != null) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
			return true;
		}		
	}

	public Boolean findByPayerCode(String payer_code) {
		Payer payer = payerRepository.findByPayerCode(payer_code).orElse(null);
		try {
			if(payer != null) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
			return true;
		}		
	}
	
}
