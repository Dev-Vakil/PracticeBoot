package com.example.dto;

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
public class UserDto {
			
	@NotBlank(message="Id should not be blank")
	private Integer id;
	
	@NotBlank(message = "Product name cannot be blank")	
	private String name;
		
	@NotBlank(message = "Product code cannot be blank")	
	private String code;
	
	@NotBlank(message = "username cannot be blank")
	private String username;
	
	@NotBlank(message = "email cannot be blank")
	@Pattern(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+[.][a-z]{2,}$", message="Invalid Email")
	private String email;
		
	@NotBlank(message = "passoword cannot be blank")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}$", message="Invalid Password")
	private String password;
	
	private String userType;
}
