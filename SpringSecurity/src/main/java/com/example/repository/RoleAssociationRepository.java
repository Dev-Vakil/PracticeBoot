package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entities.Payer;
import com.example.entities.Providers;
import com.example.entities.RoleAssociation;

public interface RoleAssociationRepository extends JpaRepository<RoleAssociation, Integer>{
	
	@Query("FROM RoleAssociation WHERE provider.providerId=?1 AND role.id = 1")
	List<RoleAssociation> findByProvider(Integer provider);

	@Query("FROM RoleAssociation WHERE payer.payerId=?1 AND role.id = 3")
	List<RoleAssociation> findByPayer(Integer payer);
	
	@Query("FROM RoleAssociation WHERE provider.providerId=?1 AND role.id = 2")
	List<RoleAssociation> findByAdmin(Integer admin);
}
