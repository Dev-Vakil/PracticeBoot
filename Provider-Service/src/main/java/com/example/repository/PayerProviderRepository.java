package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entities.PayerProvider;
import com.example.entities.PayerProviderId;

public interface PayerProviderRepository extends JpaRepository<PayerProvider, PayerProviderId>{

	@Query(value = "SELECT p.status FROM payer_provider p WHERE p.provider_id=?1 and p.payer_id=?2", nativeQuery = true)
	Optional<String> getPayerProviderStatus(Integer providerId, Integer payerId);
	
}
