package com.example.entities;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="payer_provider")
public class PayerProvider {
	
	@Id
	@ManyToOne
    @MapsId("providerId")
    @JoinColumn(name = "provider_id")
	private Providers provider;
	
	@Id
	@ManyToOne
    @MapsId("payerId")
    @JoinColumn(name = "payer_id")
	private Payer payer;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
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
