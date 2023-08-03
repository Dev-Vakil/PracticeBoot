import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PricelistService {

  pricelistUrl="http://localhost:8082/provider"  
  constructor(private http:HttpClient) { }

  allPricelist(){    
      return this.http.get(`${this.pricelistUrl}/pricelist`, {headers: new HttpHeaders({'Authorization': 'Bearer ' + localStorage.getItem("token")})
    });
  }

  downloadServicePricelist(payerId: number){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("payerId",1); 
    return this.http.get(`${this.pricelistUrl}/service/download`, { params: queryParams, headers: new HttpHeaders({'Authorization': 'Bearer ' + localStorage.getItem("token")})
    });
  }

  uploadServicePricelist(file:FormData){ 
    return this.http.post(`${this.pricelistUrl}/service/upload`,file,{ headers: new HttpHeaders({'Authorization': 'Bearer ' + localStorage.getItem("token")})   
   });
  }
}
