package com.example.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="role_association")
public class RoleAssociation {
	
	 @Column(unique=true)
	 @Id
	 @GeneratedValue
	 private Integer id;
	 
	 @ManyToOne	 
	 @JoinColumn(name="provider_id")
	 private Providers provider; 
	 
	 @ManyToOne
	 @JoinColumn(name="role_id")
	 private Role role;
	 	 
}
