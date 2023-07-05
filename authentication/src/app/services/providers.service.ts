import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ProvidersService {

  providersUrl="http://localhost:8080/provider"  
  constructor(private http:HttpClient) { }

  allProviders(){
    return this.http.get(`${this.providersUrl}/providers`, { headers: new HttpHeaders({'Authorization': 'Bearer ' + localStorage.getItem("token")})
    });
  }

  allPayers(){
    return this.http.get(`${this.providersUrl}/payers`, { headers: new HttpHeaders({'Authorization': 'Bearer ' + localStorage.getItem("token")})
    });
  }

  savePayerProviders(credentials:object){  
    return this.http.post(`${this.providersUrl}/payerProvider`,credentials,{ headers: new HttpHeaders({'Authorization': 'Bearer ' + localStorage.getItem("token")})
    });
  }

  getPayerProviders(){
    return this.http.get(`${this.providersUrl}/payerProvider`, { headers: new HttpHeaders({'Authorization': 'Bearer ' + localStorage.getItem("token")})
    });
  }
}
