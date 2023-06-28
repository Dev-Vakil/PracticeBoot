package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entities.Providers;

public interface ProvidersRepository extends JpaRepository<Providers, Integer>{
	
	Optional<Providers> findByEmail(String email);

//	@Query(value = "SELECT * FROM providers WHERE providerCode=?1", nativeQuery = true)
	Optional<Providers> findByProviderCode(String provider_code);
}
