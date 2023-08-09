package com.example.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entities.Pricelist;
import com.example.entities.ServicePricelist;

public interface ServicePricelistRepo extends JpaRepository<ServicePricelist, Integer>{

	Optional<ServicePricelist> findByServiceCodeAndPricelist(String string, Pricelist pricelist);

//	void deleteByPricelist(@Param("pricelist") Pricelist pricelist);

	@Transactional
	@Modifying
	@Query("update ServicePricelist s set s.isDeleted = true where pricelist in(from Pricelist p where p.payerId = :payerId)")
	void deleteServicePricelist(@Param("payerId") Integer payerId);

	Page<ServicePricelist> findByIsDeletedFalse(Pageable pageable);
	
}
