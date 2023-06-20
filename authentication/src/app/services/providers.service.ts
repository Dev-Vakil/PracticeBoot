import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ProvidersService {

  providersUrl="http://localhost:8081/providers"  
  constructor(private http:HttpClient) { }

  userDetails(){
    return this.http.post(`${this.providersUrl}/fetchUserDetails`,localStorage.getItem("token"));
  }

}
