package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entities.Payer;
import com.example.entities.Providers;
import com.example.entities.RoleAssociation;

public interface RoleAssociationRepository extends JpaRepository<RoleAssociation, Integer>{
	
	@Query(value = "SELECT * FROM role_association WHERE provider_id=?1 AND role_id = 1", nativeQuery = true)
	List<RoleAssociation> findByProvider(Integer provider);

	@Query(value = "SELECT * FROM role_association WHERE payer_id=?1 AND role_id = 3", nativeQuery = true)
	List<RoleAssociation> findByPayer(Integer payer);
	
	@Query(value = "SELECT * FROM role_association WHERE provider_id=?1 AND role_id = 2", nativeQuery = true)
	List<RoleAssociation> findByAdmin(Integer admin);
}
