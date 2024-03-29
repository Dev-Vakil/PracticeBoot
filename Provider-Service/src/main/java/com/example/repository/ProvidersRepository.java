package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.example.entities.Providers;

public interface ProvidersRepository extends JpaRepository<Providers, Integer>, JpaSpecificationExecutor<Providers> {

	Optional<Providers> findByEmail(String email);

	List<Providers> findAllByEmailLikeOrProviderNameLike(String email, String providerName);
}
