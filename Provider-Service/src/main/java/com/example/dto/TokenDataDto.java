package com.example.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class TokenDataDto {	

	 private String providerName;
	 private String providerCode;	
	 private String username;	 
	 private String email;
	 private String roles;
	 
}
