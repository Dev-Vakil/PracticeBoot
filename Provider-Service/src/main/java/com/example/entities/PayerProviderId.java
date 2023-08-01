package com.example.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@Embeddable
public class PayerProviderId implements Serializable{
	
	@ManyToOne	
    @MapsId("providerId")
    @JoinColumn(name = "provider_id")
	private Providers provider;
	
	@ManyToOne	
    @MapsId("payerId")
    @JoinColumn(name = "payer_id")
	private Payer payer;	
	
	public PayerProviderId(Providers provider, Payer payer) {
		this.payer = payer;
		this.provider = provider;
	}
}