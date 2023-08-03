package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.AvailableServicesPricelist;

@Repository
public interface AvailableServicePricelistRepo extends JpaRepository<AvailableServicesPricelist, Integer>{
	
	Optional<List<AvailableServicesPricelist>> findAllByPayerId(Integer payerId);
		
}
