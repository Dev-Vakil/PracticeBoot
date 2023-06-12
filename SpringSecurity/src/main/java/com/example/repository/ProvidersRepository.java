package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Providers;

public interface ProvidersRepository extends JpaRepository<Providers, Integer>{
	
	Optional<Providers> findByEmail(String email);
}
