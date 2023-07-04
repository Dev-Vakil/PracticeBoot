package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Payer;

public interface PayerRepository extends JpaRepository<Payer, Integer>{

	Optional<Payer> findByEmail(String email);

	Optional<Payer> findByPayerCode(String payer_code);
	
}
