package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.dto.PayerFilterDto;
import com.example.entities.Payer;

public interface PayerRepository extends JpaRepository<Payer, Integer>{

	@Query(value = "SELECT * FROM payer p WHERE (p.email LIKE %:filter% OR p.payer_name LIKE %:filter%) AND p.payer_id IN (select payer_id from role_association where role_id=3)", nativeQuery = true)
	Optional<List<Payer>> findAllPayers(@Param("filter") String filter);
	
}
