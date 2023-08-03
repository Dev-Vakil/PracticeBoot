package com.example.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="pricelist", uniqueConstraints = { @UniqueConstraint(columnNames = { "provider_id", "payer_id" }) })
public class Pricelist {
	
	@Column(unique=true,name="pricelist_id")
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer pricelistId;
	
	@Column(name="provider_id", nullable=false)
	private Integer providerId;
	
	@Column(name="payer_id", nullable=false)
	private Integer payerId;
	
	@Column(name="status", nullable=false)
	@Enumerated(EnumType.STRING)
	private Status status = Status.NEW;
	
	public enum Status{
		NEW,ACTIVE,DISABLE
	}
	
	@Column(name="is_deleted")
	private Boolean isDeleted;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "upload_date", updatable = true, nullable = true)
	private Date uploadDate;

	@Column(name="uploaded_by", nullable = false)
	private String uploadedBy;
	
    @UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_updated_date", updatable = true, nullable = true)
	private Date lastUpdatedDate;
    
    @JsonBackReference
    @OneToMany(mappedBy="pricelist")
    private List<ServicePricelist> servicePricelist;
}
