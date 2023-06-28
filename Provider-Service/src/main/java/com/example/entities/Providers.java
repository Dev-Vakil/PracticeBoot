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
		
	 private Integer providerId;	
	 private String providerName;	 		 
	 private String providerCode;	 	 		
	 private String username;	 		 
	 private String password;	 	
	 private String email;	 
	 private Boolean isActive;		
	 private Date createdAt;
	 private Date modifiedAt;

     public enum Role{
    	 USER,ADMIN
     }
	  
}
