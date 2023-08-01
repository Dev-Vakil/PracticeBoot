package com.example.entities;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="payer")
public class Payer {
	
	@Column(unique=true,name="payer_id")
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer payerId;
	
	@Column(name="payer_name", unique=true, nullable = false, length = 128)	 
	private String payerName;
	
	@Column(name="payer_code", unique=true,nullable = false, length = 30)	
	private String payerCode;
	
	@Column(unique=true, nullable = false, length=256)	
	private String email;
	
	@Column(nullable = false, length = 128)
	private String password;
    
	@Column(name="is_active")
	private Boolean isActive;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", updatable = true, nullable = true)
	private Date createdAt;

    @UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_at", updatable = true, nullable = true)
	private Date modifiedAt;
    
    @JsonBackReference
    @OneToMany(mappedBy="payer")
    private List<RoleAssociation> roleAssociation;
    
}
