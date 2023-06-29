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
    // return this.http.get(`${this.providersUrl}/providers`,localStorage.getItem("token"));
    
  }

}
