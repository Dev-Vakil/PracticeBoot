package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.entities.ServicePricelist;
import com.example.entities.ServicePricelist.Status;
import com.example.repository.ServicePricelistRepo;

@Service
public class ServicePricelistService {
	
	@Autowired
	private ServicePricelistRepo servicePricelistRepo;

	public Page<ServicePricelist> getServicePricelistPage(Pageable pageable, Integer pricelistId, String status) {				
		return servicePricelistRepo.findByPricelist_PricelistIdAndStatusAndIsDeletedFalse(pageable,pricelistId,Status.valueOf(status));
	}
	
}
