package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entities.Providers;

public interface ProvidersRepository extends JpaRepository<Providers, Integer>, JpaSpecificationExecutor<Providers>{
		
}
//@Query(value = "SELECT * FROM providers p WHERE p.provider_id IN (select provider_id from role_association where role_id=1 AND(p.email LIKE %:filter% OR p.provider_name LIKE %:filter%)", nativeQuery = true)
