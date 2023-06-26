package com.example.entities;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Providers{
	
	
	 private Integer provider_id;
	 
	
	 private String provider_name;
	 
	
	 
	 private String provider_code;	 
	 
		
	 private String username;
	 
		 
	 private String password;
	 
	
	 private String email;
	 
	 private Boolean is_active;
	
	
	 private Date created_at;
	 private Date modified_at;

     public enum Role{
    	 USER,ADMIN
     }
	  
}
