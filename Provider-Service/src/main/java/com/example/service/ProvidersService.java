package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.dto.TokenDataDto;
import com.example.entities.Payer;
import com.example.entities.Providers;
import com.example.repository.ProvidersRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ProvidersService {
	
	@Autowired
	private ProvidersRepository providerRepository;
	
	public ResponseEntity<TokenDataDto> fetchUserDetails(HttpServletRequest request){				
		TokenDataDto data = new TokenDataDto();
		data.setProviderName(request.getHeader("name"));
		data.setProviderCode(request.getHeader("code"));
		data.setUsername(request.getHeader("username"));
		data.setEmail(request.getHeader("email"));		
		data.setRoles(request.getHeader("roles"));
		return ResponseEntity.ok(data);
	}
	
	public ResponseEntity<List<Providers>> getProviders(String providerFilter){
		try {
			return ResponseEntity.ok(providerRepository.findAll(hasRoleUser().and(hasEmail(providerFilter).or(hasProviderName(providerFilter)))));			
		}catch(Exception e) {			
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}	
	
	static Specification<Providers> hasProviderName(String name) {
	    return (provider, cq, cb) -> cb.like(provider.get("providerName"), "%" + name + "%");	    
	}
	
	static Specification<Providers> hasEmail(String email) {
	    return (provider, cq, cb) -> cb.like(provider.get("email"), "%" + email + "%");
	}
	
	static Specification<Providers> hasRoleUser() {
	    return (provider, cq, cb) -> cb.equal(provider.get("roleAssociation").get("role").get("id"), 1);
	}

}
