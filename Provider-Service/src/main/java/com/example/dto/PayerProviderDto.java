package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PayerProviderDto {
	
	@NotBlank(message = "provider cannot be blank")
	private Integer provider;
	
	@NotBlank(message = "Payer cannot be blank")
	private Integer payer;	
	
	private Boolean status;
}
