package com.example.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="role")
public class Role {
	
	 @Column(unique=true)
	 @Id
	 @GeneratedValue(strategy= GenerationType.IDENTITY)
	 private Integer id;
	 
	 @Column(unique=true, length=15)
	 private String name;
	 
	 @JsonBackReference
	 @OneToMany(mappedBy="role")
     private List<RoleAssociation> roleAssociation;
}
