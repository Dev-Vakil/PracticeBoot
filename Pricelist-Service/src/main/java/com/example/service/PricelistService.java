package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.entities.Pricelist;
import com.example.repository.PricelistRepo;

@Service
public class PricelistService {

	@Autowired
	private PricelistRepo pricelistRepo;
	
	public List<Pricelist> getAllPriceList() {		
		return pricelistRepo.findAll();
	}

	public Page<Pricelist> getPricelistPage(Pageable pageable) {
		return pricelistRepo.findByIsDeletedFalse(pageable);				
	}
	
}
