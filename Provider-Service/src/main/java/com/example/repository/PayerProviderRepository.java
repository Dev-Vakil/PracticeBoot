package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entities.Payer;
import com.example.entities.PayerProvider;
import com.example.entities.PayerProviderId;
import com.example.entities.Providers;

public interface PayerProviderRepository extends JpaRepository<PayerProvider, PayerProviderId> {

//	@Query("SELECT p.status FROM PayerProvider p WHERE p.payerProviderId.provider.providerId=?1 and p.payerProviderId.payer.payerId=?2")
//	Optional<String> getPayerProviderStatus(Integer providerId, Integer payerId);

	Optional<PayerProvider> findByPayerProviderId_Payer_PayerIdAndPayerProviderId_Provider_ProviderId(Integer providerId, Integer payerId);

	@Query("SELECT p.payerProviderId.payer FROM PayerProvider p WHERE p.payerProviderId.provider=?1 AND (p.payerProviderId.payer.payerName LIKE %?2% OR p.payerProviderId.payer.email LIKE %?2%)")
	Optional<List<Payer>> getPayerByProvider(Providers provider, String search);

}

//	Optional<PayerProvider> findPayerProviderId_PayerByPayerProviderId_ProviderAndPayerProviderId_Payer_PayerNameLikeOrPayerProviderId_ProviderAndPayerProviderId_Payer_EmailLike(Providers provider, String search);
//@Query("SELECT p.payerProviderId.payer FROM PayerProvider p WHERE p.payerProviderId.provider=?1 AND p.payerProviderId.payer.email LIKE ?2")