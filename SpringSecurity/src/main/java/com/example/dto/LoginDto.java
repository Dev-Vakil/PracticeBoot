package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LoginDto {
	
	@NotBlank(message = "email cannot be blank")
	private String email;
		
	@NotBlank(message = "passoword cannot be blank")
	private String password;
	
	@NotBlank(message = "role cannot be blank")
	private String role;
}
