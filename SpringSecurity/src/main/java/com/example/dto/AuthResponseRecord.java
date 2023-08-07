package com.example.dto;

import java.util.List;

public record AuthResponseRecord(String token, List<String> roles ) {
	
}
