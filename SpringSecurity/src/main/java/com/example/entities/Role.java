package com.example.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="role")
public class Role {
	
	 @Column(unique=true)
	 @Id
	 @GeneratedValue
	 private Integer id;
	 
	 @Column(unique=true, length=15)
	 private String name;
	 
	 @OneToMany(mappedBy="role")
     private List<RoleAssociation> roleAssociation;
}
