package com.example.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.naming.directory.InvalidAttributesException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

@RequestMapping("provider/associatedPayer/{payerId}/pricelist")
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
		try {			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD:HH:MM:SS");
			String fileType = "attachment; filename=pricelist_details_" + dateFormat.format(new Date()) + ".xls";
			response.setHeader("Content-Disposition", fileType);
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM.getType());	        
			
			availableServicePricelistService.downloadAvailableServicePricelist(response, payerId);
		}catch(Exception e) {
			response.sendError(404,"File Not Found");
		}
	}	
		
	@PostMapping("/upload")
	public ResponseEntity<?> uploadNewServicePricelist(@RequestParam("file") MultipartFile file, @PathVariable("payerId") String payerId) throws IOException{
		try {			
			Integer id  = Integer.parseInt(payerId);
			Boolean result = availableServicePricelistService.uploadServicePricelist(file,id);
			return ResponseEntity.ok(result);
		}		
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e);
		}
	}			

	@GetMapping("/sampleDownload")
	public void downloadSampleFile(HttpServletResponse response, @PathVariable("payerId") Integer payerId){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD:HH:MM:SS");
        String fileType = "attachment; filename=pricelist_details_" + dateFormat.format(new Date()) + ".xls";
        response.setHeader("Content-Disposition", fileType);
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM.getType());	 
        
		availableServicePricelistService.downloadSampleFile(response, payerId);
	}
	
	@GetMapping("")
	public ResponseEntity<?> getAllAvailableServicePricelist(){
		try {
			List<AvailableServicesPricelist> availServices = availableServicePricelistService.getAll();
			return ResponseEntity.ok(availServices);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("pricelist list not found");
		}
	}
	
}

