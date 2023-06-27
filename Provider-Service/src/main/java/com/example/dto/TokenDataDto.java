package com.example.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class TokenDataDto {	

	 private String provider_name;
	 private String provider_code;	
	 private String username;	 
	 private String email;
	 private String roles;
	 
}
