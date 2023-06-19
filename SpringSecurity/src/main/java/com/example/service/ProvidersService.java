package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.entities.Providers;
import com.example.repository.ProvidersRepository;

@Service
public class ProvidersService implements UserDetailsService{

	@Autowired
	private ProvidersRepository providersRepository;
	
	@Override
	public Providers loadUserByUsername(String email) throws UsernameNotFoundException {		
		return providersRepository.findByEmail(email)
				.orElseThrow(()-> new UsernameNotFoundException("user not found"));
	}

	
	public Boolean findProviderCode(String provider_code) {
		Providers provider = providersRepository.findByProvider__code(provider_code).orElse(null);
		try {
			if(provider != null) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
			return false;
		}
		
	}
	
}
