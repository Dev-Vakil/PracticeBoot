package com.example.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="role_association")
public class RoleAssociation {
	
	 @Column(unique=true)
	 @Id
	 @GeneratedValue(strategy= GenerationType.IDENTITY)
	 private Integer id;
	 
	 @JsonManagedReference
	 @ManyToOne	 
	 @JoinColumn(name="provider_id")
	 private Providers provider; 
	 
	 @JsonManagedReference
	 @ManyToOne	 
	 @JoinColumn(name="payer_id",nullable = true)
	 @Nullable
	 private Payer payer; 
	 	 
	 @JsonManagedReference
	 @ManyToOne
	 @JoinColumn(name="role_id",nullable = true)
	 private Role role;
	 	 
}
