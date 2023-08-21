package com.example.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.commons.math3.exception.NoDataException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.entities.AvailableServicesPricelist;
import com.example.entities.Pricelist;
import com.example.entities.Pricelist.Status;
import com.example.entities.ServicePricelist;
import com.example.repository.AvailableServicePricelistRepo;
import com.example.repository.PricelistRepo;
import com.example.repository.ServicePricelistRepo;

@Service
public class AvailableServicePricelistService {

	@Autowired
	private AvailableServicePricelistRepo availableServicePricelistRepo;
	
	@Autowired
	private PricelistRepo pricelistRepo;
	
	@Autowired
	private ServicePricelistRepo servicePricelistRepo;

	public void downloadAvailableServicePricelist(HttpServletResponse response, Integer payerId) {
		List<AvailableServicesPricelist> servicesPriceList = availableServicePricelistRepo.findAllByPayerId(payerId)
				.orElse(null);
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("list");
		
		CellStyle cellStyle = workbook.createCellStyle();
        //set border to table
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
		
		// put data of available service pricelist into map 
		Map<Integer, Object[]> data = new TreeMap<Integer, Object[]>();
		int num = 0;
		data.put(num++, new Object[] { "Service Code", "Service Description", "Default Price" });
		if(servicesPriceList.isEmpty()) {
			throw new NoDataException();
//			data.put(num++, new Object[] { "p"+payerId+"_1", "Cancer", "1000000" });
		}
		for (AvailableServicesPricelist obj : servicesPriceList) {
			data.put(num++, new Object[] { obj.getServiceCode(), obj.getServiceDescription(), obj.getDefaultPrice() });
		}		
		
//		puting data from map to workbook
		Set<Integer> keySet = data.keySet();
		int rownum = 0;
		for (Integer key : keySet) {
			Row row = sheet.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String) {
					cell.setCellValue((String) obj);
					cell.setCellStyle(cellStyle);
				}
				else if (obj instanceof Integer) {
					cell.setCellValue((Integer) obj);
					cell.setCellStyle(cellStyle);
				}					
			}
		}
		try {			
			workbook.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Boolean uploadServicePricelist(MultipartFile file, Integer payerId) throws IOException {
		
		Map<String,Object> principal = (Map<String, Object>) SecurityContextHolder.getContext().getAuthentication().getPrincipal();		
		Integer providerId = (Integer) principal.get("id");		
		
//		List<AvailableServicesPricelist> servicesPriceList = availableServicePricelistRepo.findAllByPayerId(payerId)
//				.orElse(null);		
//		List<String> ogServiceCode = servicesPriceList.stream().map(AvailableServicesPricelist::getServiceCode)
//				.collect(Collectors.toList());
		
		FileInputStream fileData = (FileInputStream) file.getInputStream();
		XSSFWorkbook workbook = new XSSFWorkbook(fileData);
		
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.rowIterator();
		rowIterator.next();	
		
		Pricelist pricelist = pricelistRepo.findByProviderIdAndPayerId(providerId, payerId).orElse(null);
		if(pricelist != null) {			
			//soft deleted all services of the payer
			servicePricelistRepo.deleteServicePricelist(pricelist.getPricelistId());
		}
		
		List<Integer> errorList = new ArrayList<>();
		List<Integer> emptyDescriptionList = new ArrayList<>();
		List<Integer> emptypriceList = new ArrayList<>();
		
		//Iterates Every row		
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			String[] data = new String[3];
			int index = 0;
			Iterator<Cell> cellIterator = row.cellIterator();
			//Iterates every column and stores data in List of string
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();	
				 if (cell == null || cell.getCellType() == CellType.BLANK) {
					break;
				 }
				data[index] = cell.toString();						
				index++;
			}			
			
			//check for pricelist if doesn't exists makes a new entry
			if (data[0] != null) {
				if (!Pattern.matches("^p"+payerId+"_.$", data[0])) {		
					errorList.add(row.getRowNum());		
					continue;
				}
				if(data[1]==null || data[1].isEmpty() || data[1].equals("")) {
					emptyDescriptionList.add(row.getRowNum());
					continue;
				}
				if(data[2]==null || data[2].isEmpty() || data[2].equals("0")) {
					emptypriceList.add(row.getRowNum());
					continue;
				}
								
				if(pricelist == null) {
					Pricelist pricelistBuilder = Pricelist.builder()
						.payerId(payerId)
						.providerId((Integer) principal.get("id"))
						.uploadedBy((String) principal.get("name"))
						.isDeleted(false)
						.status(Status.NEW)
						.build();
					pricelist = pricelistRepo.save(pricelistBuilder);
				}			
				
				//check for service-pricelist if doesn't exists makes a new entry				
				ServicePricelist checkServicePricelist = servicePricelistRepo.findByServiceCodeAndPricelist(data[0],pricelist).orElse(new ServicePricelist());
				ServicePricelist servicePricelist = ServicePricelist.builder()
						.servicePricelistId(checkServicePricelist.getServicePricelistId())
						.serviceCode(data[0])
						.serviceDescription(data[1])
						.price(Integer.valueOf((int) Double.parseDouble(data[2])))
						.pricelist(pricelist)
						.status(com.example.entities.ServicePricelist.Status.PENDING)
						.isDeleted(false)
						.rejectionReason(checkServicePricelist.getRejectionReason())
						.createdAt(checkServicePricelist.getCreatedAt())
						.build();
				servicePricelistRepo.save(servicePricelist);	
			}
		}
		if(!errorList.isEmpty()) {
			throw new InvalidRequestException("Service Code is Invalid on line: "+errorList);
		}
		if(!emptyDescriptionList.isEmpty()) {
			throw new InvalidRequestException("Service Description is Invalid on line: "+emptyDescriptionList);
		}
		if(!emptypriceList.isEmpty()) {
			throw new InvalidRequestException("Price is Invalid on line: "+emptypriceList);
		}
		return true;
	}

	public List<AvailableServicesPricelist> getAll() {
		return availableServicePricelistRepo.findAll();		
	}

	public void downloadSampleFile(HttpServletResponse response,Integer payerId){
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("list");
		
		CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
		
		// put data of available service pricelist into map 
		Map<Integer, Object[]> data = new TreeMap<Integer, Object[]>();
		int num = 0;
		data.put(num++, new Object[] { "Service Code", "Service Description", "Default Price" });		
		data.put(num++, new Object[] { "p"+payerId+"_1", "Cancer", "1000000" });		
		
//		puting data from map to workbook
		Set<Integer> keySet = data.keySet();
		int rownum = 0;
		for (Integer key : keySet) {
			Row row = sheet.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String) {
					cell.setCellValue((String) obj);
					cell.setCellStyle(cellStyle);
				}
				else if (obj instanceof Integer) {
					cell.setCellValue((Integer) obj);
					cell.setCellStyle(cellStyle);
				}					
			}
		}
		try {			
			workbook.write(response.getOutputStream());
		} catch (Exception e) {		
			e.printStackTrace();
		}		
	}

}
