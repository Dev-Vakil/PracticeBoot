package com.example.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
