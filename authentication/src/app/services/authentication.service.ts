import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  url="http://localhost:8081/auth"

  constructor(private http:HttpClient) { }

  register(credentials:object){
      return this.http.post(`${this.url}/register`,credentials);
  }
  
  login(credentials:object){
      return this.http.post(`${this.url}/login`,credentials);
  }

  saveToken(token:string){
    localStorage.setItem("token",token);
  }  

  isLoggedIn(){
    let token = localStorage.getItem("token");
    if(token==undefined || token === "" || token == null){
      return false;
    }
    else{
      return true
    }
  }

  findProviderCode(provider_code:string){
    return this.http.post(`${this.url}/findProviderCode`,provider_code);
  }

  logout(){
    localStorage.setItem("token",'');
    window.location.href = "/login";
  }
}
