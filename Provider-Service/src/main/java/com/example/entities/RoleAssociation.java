package com.example.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

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
	 @JoinColumn(name="provider_id",nullable = true)
	 @Nullable
	 private Providers provider; 
	 
	 @JsonManagedReference
	 @ManyToOne	 
	 @JoinColumn(name="payer_id",nullable = true)
	 @Nullable
	 private Payer payer; 
	 	 
	 @JsonManagedReference
	 @ManyToOne()
	 @JoinColumn(name="role_id",nullable = true)
	 private Role role;
	 	 
}
