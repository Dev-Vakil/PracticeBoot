import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Pageable } from '../interfaces/pageable';

@Injectable({
  providedIn: 'root'
})
export class PricelistService {  
  
  uploadPricelistUrl="http://localhost:8082/payers"  
  pricelistUrl = "http://localhost:8082/pricelist"
  servicePricelistUrl = "http://localhost:8082/servicePricelist"
  constructor(private http:HttpClient) { }
  
  allPricelist(){    
    return this.http.get(`${this.uploadPricelistUrl}/`+0+`/pricelist`, {headers: new HttpHeaders({'Authorization': 'Bearer ' + localStorage.getItem("token")})
  });
  }

  downloadServicePricelist(payerId: number){    
    return this.http.get(`${this.uploadPricelistUrl}/`+payerId+`/pricelist/download`, {responseType:  'blob', headers: new HttpHeaders({'Authorization': 'Bearer ' + localStorage.getItem("token")})
  });
  }

  uploadServicePricelist(file:FormData){ 
    return this.http.post(`${this.uploadPricelistUrl}/`+file.get("payerId")+`/pricelist/upload`,file,{ headers: new HttpHeaders({'Authorization': 'Bearer ' + localStorage.getItem("token")})   
  });
  }

  getPricelist(page:Pageable) {
    let queryParams = new HttpParams().append("page",page.page);
    let queryParams2 = queryParams.append("size",page.size);
    return this.http.get(`${this.pricelistUrl}/`,{params: queryParams2, headers: new HttpHeaders({'Authorization': 'Bearer '+ localStorage.getItem("token")})
  });
  }

  getServicePricelist(page:Pageable){
    let queryParams = new HttpParams().append("page",page.page);
    let queryParams2 = queryParams.append("size",page.size);
    return this.http.get(`${this.servicePricelistUrl}/`,{params: queryParams2, headers: new HttpHeaders({'Authorization': 'Bearer '+ localStorage.getItem("token")})
  });
  }
}
