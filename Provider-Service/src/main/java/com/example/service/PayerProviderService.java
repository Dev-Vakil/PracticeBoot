package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.dto.PayerProviderDto;
import com.example.entities.Payer;
import com.example.entities.PayerProvider;
import com.example.entities.PayerProvider.Status;
import com.example.entities.PayerProviderId;
import com.example.entities.Providers;
import com.example.repository.PayerProviderRepository;
import com.example.repository.PayerRepository;
import com.example.repository.ProvidersRepository;

@Service
public class PayerProviderService {
	
	@Autowired
	private PayerProviderRepository payerProviderRepository;
	
	@Autowired
	private PayerRepository payerRepository;
	
	@Autowired
	private ProvidersRepository providersRepository;

	public ResponseEntity<Boolean> save(PayerProviderDto details) {
		try {
			Status status;
			if(Boolean.TRUE.equals(details.getStatus())){
				status = Status.ACTIVE;
			}
			else {
				status = Status.INACTIVE;
			}			
			PayerProvider payerProvider = PayerProvider.builder()
			.payerProviderId(
					PayerProviderId.builder()
					.payer(payerRepository.findById(details.getPayer()).orElse(null))
					.provider(providersRepository.findById(details.getProvider()).orElse(null))		
					.build()
			)									
			.status(status)
			.build();
			payerProviderRepository.save(payerProvider);
			return ResponseEntity.ok(true);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(false);
		}		
	}

	public ResponseEntity<List<PayerProvider>> getAll() {
		try {			
			List<PayerProvider> ans = payerProviderRepository.findAll();
			return ResponseEntity.ok(ans);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(null);
		}
	}

	public ResponseEntity<Boolean> getPayerProviderStatus(Integer providerId, Integer payerId) {
		try {
			String status = payerProviderRepository.getPayerProviderStatus(providerId,payerId).orElse(null);
			if(status == null || status.equals("INACTIVE")) {
				return ResponseEntity.ok(false);
			}
			else{	
				return ResponseEntity.ok(true);
			}			
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(false);
		}
	}
	
	public ResponseEntity<List<Payer>> associatedPayers(String email,String search) {
		try {			
			Optional<Providers> provider = providersRepository.findByEmail(email);
			if(provider.isPresent()) {				
				return ResponseEntity.ok(payerProviderRepository.getPayerByProvider(provider.get(),search).orElse(null));							
			}
			return null;
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
