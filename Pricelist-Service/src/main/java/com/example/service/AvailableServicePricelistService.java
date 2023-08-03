package com.example.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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

	public Boolean downloadAvailablePricelistService(Integer payerId) {
		List<AvailableServicesPricelist> servicesPriceList = availableServicePricelistRepo.findAllByPayerId(payerId)
				.orElse(null);

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("list");

		Map<Integer, Object[]> data = new TreeMap<Integer, Object[]>();
		int num = 0;
		data.put(num++, new Object[] { "Service Code", "Service Description", "Default Price" });
		for (AvailableServicesPricelist obj : servicesPriceList) {
			data.put(num++, new Object[] { obj.getServiceCode(), obj.getServiceDescription(), obj.getDefaultPrice() });
		}

		Set<Integer> keySet = data.keySet();
		int rownum = 0;
		for (Integer key : keySet) {
			Row row = sheet.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);
			}
		}
		try {
			String home = System.getProperty("user.home");
			FileOutputStream out = new FileOutputStream(new File(home + "/Downloads/DemoList.xlsx"));
			workbook.write(out);
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Boolean uploadPricelistService(MultipartFile file, Integer payerId) throws IOException {
		
		List<AvailableServicesPricelist> servicesPriceList = availableServicePricelistRepo.findAllByPayerId(payerId)
				.orElse(null);		
		List<String> ogServiceCode = servicesPriceList.stream().map(AvailableServicesPricelist::getServiceCode)
				.collect(Collectors.toList());
			
		FileInputStream fileData = (FileInputStream) file.getInputStream();
		XSSFWorkbook workbook = new XSSFWorkbook(fileData);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.rowIterator();
		rowIterator.next();
		//Iterates Every row
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			String[] data = new String[3];
			int index = 0;
			Iterator<Cell> cellIterator = row.cellIterator();
			//Iterates every column and stores data in List of string
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				data[index] = cell.toString();
				if (!ogServiceCode.contains(data[0]))
					return false;
				index++;
			}
			
			//get Principal from security context
			Map<String, Object> map = (Map<String, Object>) SecurityContextHolder.getContext().getAuthentication().getPrincipal();						
			Map<String, Object> principal =  (Map<String, Object>) map.get("principal");
			
			//check for pricelist if doesn't exists makes a new entry
			if (data[0] != null) {		
				Pricelist pricelist =  pricelistRepo.findByProviderIdAndPayerId((Integer) principal.get("id"),payerId).orElse(null);
				if(pricelist == null) {
					Pricelist pricelistBuilder = Pricelist.builder()
						.payerId(payerId)
						.providerId((Integer) principal.get("id"))
						.uploadedBy((String) principal.get("name"))
						.status(Status.NEW)
						.build();
					pricelist = pricelistRepo.save(pricelistBuilder);
				}			
				//check for servicepricelist if doesn't exists makes a new entry
				ServicePricelist checkServicePricelist = servicePricelistRepo.findByServiceCodeAndPricelist(data[0],pricelist).orElse(null);
				if(checkServicePricelist == null) {
					ServicePricelist servicePricelist = ServicePricelist.builder()
							.serviceCode(data[0])
							.serviceDescription(data[1])
							.price(Integer.valueOf((int) Double.parseDouble(data[2])))
							.pricelist(pricelist)
							.status(com.example.entities.ServicePricelist.Status.PENDING)
							.isDeleted(false)
							.build();
					servicePricelistRepo.save(servicePricelist);
				}
			}
		}
		return true;
	}

}