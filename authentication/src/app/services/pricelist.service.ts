import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PricelistService {

  pricelistUrl="http://localhost:8082/payers"  
  constructor(private http:HttpClient) { }

  allPricelist(){    
      return this.http.get(`${this.pricelistUrl}/`+0+`/pricelist`, {headers: new HttpHeaders({'Authorization': 'Bearer ' + localStorage.getItem("token")})
    });
  }

  downloadServicePricelist(payerId: number){    
    return this.http.get(`${this.pricelistUrl}/`+payerId+`/pricelist/download`, {responseType:  'blob', headers: new HttpHeaders({'Authorization': 'Bearer ' + localStorage.getItem("token")})
    });
  }

  uploadServicePricelist(file:FormData){ 
    return this.http.post(`${this.pricelistUrl}/`+file.get("payerId")+`/pricelist/upload`,file,{ headers: new HttpHeaders({'Authorization': 'Bearer ' + localStorage.getItem("token")})   
   });
  }
}
