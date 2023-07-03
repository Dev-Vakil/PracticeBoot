package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Payer;

public interface PayerRepository extends JpaRepository<Payer, Integer>{
	
}
