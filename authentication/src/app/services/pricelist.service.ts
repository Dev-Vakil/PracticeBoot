import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Pageable } from '../interfaces/pageable';

@Injectable({
  providedIn: 'root'
})
export class PricelistService {  
  
  uploadPricelistUrl="http://localhost:8082/provider/associatedPayer"  
  providerPricelistUrl = "http://localhost:8082/provider/pricelist"
  payerPricelistUrl = "http://localhost:8082/payer/pricelist"
  constructor(private http:HttpClient) { }
  
  allPricelist(){    
    return this.http.get(`${this.uploadPricelistUrl}/`+0+`/pricelist`, {headers: new HttpHeaders({'Authorization': 'Bearer ' + localStorage.getItem("token")})
  });
  }

  downloadServicePricelist(payerId: number){    
    return this.http.get(`${this.uploadPricelistUrl}/`+payerId+`/pricelist/download`, {responseType:  'blob', headers: new HttpHeaders({'Authorization': 'Bearer ' + localStorage.getItem("token")})
  });
  }
  
  downloadServicePricelistSample(payerId: number){    
    return this.http.get(`${this.uploadPricelistUrl}/`+payerId+`/pricelist/sampleDownload`, {responseType:  'blob', headers: new HttpHeaders({'Authorization': 'Bearer ' + localStorage.getItem("token")})
  });
  }

  uploadServicePricelist(file:FormData){ 
    return this.http.post(`${this.uploadPricelistUrl}/`+file.get("payerId")+`/pricelist/upload`,file,{ headers: new HttpHeaders({'Authorization': 'Bearer ' + localStorage.getItem("token")})   
  });
  }

  getPricelist(page:Pageable, providerId:number) {
    let queryParams = new HttpParams().append("page",page.page);
    let queryParams2 = queryParams.append("size",page.size);
    let queryParams3 = queryParams2.append("providerId",providerId);
    return this.http.get(`${this.providerPricelistUrl}/`,{params: queryParams3, headers: new HttpHeaders({'Authorization': 'Bearer '+ localStorage.getItem("token")})
  });
  }
  
  getPayerPricelist(page:Pageable, payerId:number) {
    let queryParams = new HttpParams().append("page",page.page);
    let queryParams2 = queryParams.append("size",page.size);
    let queryParams3 = queryParams2.append("payerId",payerId);
    return this.http.get(`${this.payerPricelistUrl}/`,{params: queryParams3, headers: new HttpHeaders({'Authorization': 'Bearer '+ localStorage.getItem("token")})
  });
  }

  getServicePricelist(page:Pageable, pricelistId:number, status:string){
    let queryParams1 = new HttpParams().append("page",page.page);
    let queryParams2 = queryParams1.append("size",page.size);
    let queryParams3 = queryParams2.append("pricelistId",pricelistId);
    let queryParams4 = queryParams3.append("status",status);
    return this.http.get(`${this.providerPricelistUrl}/service`,{params: queryParams4, headers: new HttpHeaders({'Authorization': 'Bearer '+ localStorage.getItem("token")})
  });
  }
}
