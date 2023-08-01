package com.example.entities;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder
@Table(name="payer_provider")
public class PayerProvider {
	
	@EmbeddedId
	private PayerProviderId payerProviderId;
	
	@Enumerated(EnumType.STRING)
	private Status status = Status.INACTIVE;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", updatable = true, nullable = true)
	private Date createdAt;

    @UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_at", updatable = true, nullable = true)
	private Date modifiedAt;
	
	public enum Status{
		ACTIVE,INACTIVE
	}		
}