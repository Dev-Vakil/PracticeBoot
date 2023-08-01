package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.dto.TokenDataDto;
import com.example.entities.Providers;
import com.example.repository.ProvidersRepository;

import javax.servlet.http.HttpServletRequest;

@Service
public class ProvidersService {

	@Autowired
	private ProvidersRepository providerRepository;

	public ResponseEntity<TokenDataDto> fetchUserDetails(HttpServletRequest request) {
		TokenDataDto data = new TokenDataDto();
		data.setProviderName(request.getHeader("name"));
		data.setProviderCode(request.getHeader("code"));
		data.setUsername(request.getHeader("username"));
		data.setEmail(request.getHeader("email"));
		data.setRoles(request.getHeader("roles"));
		return ResponseEntity.ok(data);
	}

	public ResponseEntity<List<Providers>> getProviders(String providerFilter) {
		try {
			return ResponseEntity.ok(providerRepository.findAll(hasRoleUser().and(hasFilter(providerFilter))));
//			return ResponseEntity
//					.ok(providerRepository.findAllByEmailLikeOrProviderNameLike(providerFilter, providerFilter));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}

	private static Specification<Providers> hasFilter(String filter) {
		return (provider, cq, cb) -> cb.or(cb.like(provider.get("providerName"), "%" + filter + "%"),
				cb.like(provider.get("email"), "%" + filter + "%"));
	}

	private static Specification<Providers> hasRoleUser() {
		return (provider, cq, cb) -> cb.equal(provider.join("roleAssociation").get("role").get("id"), 1);
	}

}
