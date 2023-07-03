package com.example.dto;

import java.util.List;

import com.example.entities.RoleAssociation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {
	private String token;
	private List<String> roles;
}
