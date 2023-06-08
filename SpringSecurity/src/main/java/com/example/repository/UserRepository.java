package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.models.User;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer>{
	
	Optional<User> findByEmail(String email);
}
