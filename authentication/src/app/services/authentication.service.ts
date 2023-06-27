import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  authUrl="http://localhost:8080/auth"  

  constructor(private http:HttpClient) { }

  register(credentials:object){
      return this.http.post(`${this.authUrl}/register`,credentials);
  }
  
  login(credentials:object){
      return this.http.post(`${this.authUrl}/login`,credentials);
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
    return this.http.post(`${this.authUrl}/findProviderCode`,provider_code);
  }

  findEmail(email: string){    
    return this.http.post(`${this.authUrl}/findEmail`,email);
  }

  logout(){
    localStorage.setItem("token",'');
    window.location.href = "/login";
  }
}
