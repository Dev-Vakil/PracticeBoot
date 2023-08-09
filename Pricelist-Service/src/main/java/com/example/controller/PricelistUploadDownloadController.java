package com.example.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.entities.AvailableServicesPricelist;
import com.example.entities.Pricelist;
import com.example.service.AvailableServicePricelistService;
import com.example.service.PricelistService;

@RequestMapping("/payers/{payerId}/pricelist")
@RestController
@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class PricelistUploadDownloadController {

	@Autowired
	private AvailableServicePricelistService availableServicePricelistService;
	
	@Autowired
	private PricelistService pricelistService;
	
	@GetMapping("/download")
	public void downloadAvailablePricelistService(HttpServletResponse response, @PathVariable("payerId") Integer payerId) throws IOException{		
		
		DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
        String fileType = "attachment; filename=pricelist_details_" + dateFormat.format(new Date()) + ".xls";
        response.setHeader("Content-Disposition", fileType);
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM.getType());	        
	        	
		availableServicePricelistService.downloadAvailableServicePricelist(response, payerId);
	}	
		
	@PostMapping("/upload")
	public ResponseEntity<Boolean> uploadNewServicePricelist(@RequestParam("file") MultipartFile file, @PathVariable("payerId") String payerId) throws IOException{	
		Integer id  = Integer.parseInt(payerId);
		Boolean result = availableServicePricelistService.uploadServicePricelist(file,id);
		return ResponseEntity.ok(result);
	}			

	@GetMapping("")
	public ResponseEntity<List<AvailableServicesPricelist>> getAllAvailableServicePricelist(){
		List<AvailableServicesPricelist> availServices = availableServicePricelistService.getAll();
		return ResponseEntity.ok(availServices);
	}
	
}

