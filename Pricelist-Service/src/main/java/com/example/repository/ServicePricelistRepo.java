package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.Pricelist;
import com.example.entities.ServicePricelist;

@Repository
public interface ServicePricelistRepo extends JpaRepository<ServicePricelist, Integer>{

	Optional<ServicePricelist> findByServiceCodeAndPricelist(String string, Pricelist pricelist);
	
}
