package com.example.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.config.JwtService;
import com.example.dto.ProviderDto;
import com.example.entities.Providers;
import com.example.service.ProvidersService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/providers")
@RestController
@CrossOrigin
public class ProviderController {	
	
	@Autowired
	ProvidersService providersService;
	
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("/fetchUserDetails")
	public ResponseEntity<?> userDetailsFromToken(HttpServletRequest request){	
		try {	
			final String authHeader = request.getHeader("Authorization");
			if(authHeader == null || !authHeader.startsWith("Bearer ")) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			String token = authHeader.substring(7);						
			Providers provider = new Providers();
			provider.setEmail(jwtService.extractEmail(token));
			
			ProviderDto result = new ProviderDto();
			if(jwtService.isTokenValid(token, provider)){
				Map<String,Object> claims = jwtService.extractAllClaims(token);
				result.setEmail(claims.get("provider_name").toString());
				result.setProvider_name(claims.get("provider_name").toString());
				result.setProvider_code(claims.get("provider_code").toString());
				result.setUsername(claims.get("username").toString());
				result.setEmail(claims.get("email").toString());
				result.setPassword("********");
				return ResponseEntity.ok(result);
			}
			else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
}
