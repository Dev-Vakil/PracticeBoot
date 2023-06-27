package com.example.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	 @GeneratedValue(strategy= GenerationType.AUTO)
	 private Integer id;
	 
	 @JsonBackReference
	 @ManyToOne	 
	 @JoinColumn(name="provider_id")
	 private Providers provider; 
	 
	 @JsonBackReference
	 @ManyToOne
	 @JoinColumn(name="role_id")
	 private Role role;
	 	 
}
