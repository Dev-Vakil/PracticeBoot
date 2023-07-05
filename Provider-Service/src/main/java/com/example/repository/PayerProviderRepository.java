package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.PayerProvider;
import com.example.entities.PayerProviderId;

public interface PayerProviderRepository extends JpaRepository<PayerProvider, PayerProviderId>{
	
}
