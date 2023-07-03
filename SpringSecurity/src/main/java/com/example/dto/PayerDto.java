package com.example.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PayerDto {
	
	@NotBlank(message = "Payer name cannot be blank")		 
	private String payerName;
	
	@NotBlank(message = "Payer name cannot be blank")	
	private String payerCode;
	
	@NotBlank(message = "email cannot be blank")
	@Pattern(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+[.][a-z]{2,}$", message="Invalid Email")
	private String email;
		
	@NotBlank(message = "passoword cannot be blank")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}$", message="Invalid Password")
	private String password;
}
