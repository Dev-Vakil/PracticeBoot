package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.Pricelist;

@Repository
public interface PricelistRepo extends JpaRepository<Pricelist, Integer>{

	Optional<Pricelist> findByProviderIdAndPayerId(Integer integer, Integer payerId);

}