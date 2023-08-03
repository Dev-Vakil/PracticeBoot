package com.example.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="available_pricelist_services")
public class AvailableServicesPricelist {
	
	@Id
	@Column(unique=true,name="id")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="service_code", nullable = false, length = 64)
    private String serviceCode;
	
	@Column(name="service_description", length = 256)
    private String serviceDescription;
    
	@Column(name="default_price")
	private Integer defaultPrice;
	
	@Column(name="payer_id")
    private Integer payerId;
    
    @CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", updatable = true, nullable = true)
	private Date createdAt;

    @UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at", updatable = true, nullable = true)
	private Date updatedAt; 
}
