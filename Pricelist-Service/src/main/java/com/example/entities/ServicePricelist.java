package com.example.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="service_pricelist")
public class ServicePricelist {
	
	@Id
	@Column(name="service_pricelist_id", unique=true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer servicePricelistId;
	
	@Column(name="service_code", length=64, nullable=false)
    private String serviceCode;
	
	@Column(name="service_description", length=256)
    private String serviceDescription;
    
	@Column(nullable = false)
	private Integer price;

	public enum Status{
		PENDING,APPROVED,REJECTED
	}
	
	@Column(name="status", nullable=false)
	@Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;
    
	@Column(name="is_deleted", nullable = false)
    private Boolean isDeleted = false;
		
	@ManyToOne
	@JsonManagedReference	 	 
	@JoinColumn(name="pricelist_id",nullable = false)	
    private Pricelist pricelist;
    
	@Column(name="rejection_reason", length=256)
	private String rejectionReason;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", updatable = true, nullable = true)
	private Date createdAt;

    @UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at", updatable = true, nullable = true)
	private Date updatedAt;       
}
