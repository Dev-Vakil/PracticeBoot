package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Providers;
import com.example.entities.RoleAssociation;

public interface RoleAssociationRepository extends JpaRepository<RoleAssociation, Integer>{
	
	List<RoleAssociation> findByProvider(Providers provider);
}
