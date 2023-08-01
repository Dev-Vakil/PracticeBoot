package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.entities.Providers;
import com.example.repository.ProvidersRepository;

@Service
public class ProvidersService{

	@Autowired
	private ProvidersRepository providersRepository;
	
	public Providers loadUserByUsername(String email) throws UsernameNotFoundException {		
		return providersRepository.findByEmail(email)
				.orElseThrow(()-> new UsernameNotFoundException("user not found"));
	}

	
	public Boolean findProviderCode(String provider_code) {
		Providers provider = providersRepository.findByProviderCode(provider_code).orElse(null);		
		try {
			return (provider != null);			
		}
		catch(Exception e) {
			return true;
		}		
	}
	
}
