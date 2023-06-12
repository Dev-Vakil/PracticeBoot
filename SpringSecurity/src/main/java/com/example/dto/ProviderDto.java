package com.example.dto;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProviderDto {
	private String username;	
	private String email;
	private String password;
}
