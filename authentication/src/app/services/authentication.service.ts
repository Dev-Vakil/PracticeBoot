import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  authUrl="http://localhost:8081/auth"  

  constructor(private http:HttpClient) { }

  register(credentials:object){
      return this.http.post(`${this.authUrl}/register`,credentials);
  }
  
  login(credentials:object){        
      return this.http.post(`${this.authUrl}/login`,credentials);
  }

  saveToken(token:string,roles:any){
    localStorage.setItem("token",token);        
    localStorage.setItem("roles",roles)
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

  isUserLoggedIn(){
    let token = localStorage.getItem("token");
    if(token==undefined || token === "" || token == null){
      return false;
    }
    else{
      if(localStorage.getItem("roles")?.includes("USER") || localStorage.getItem("roles")?.includes("PAYER"))
        return true;
      else
        return false;
    }
  }

  isAdminLoggedIn(){
    let token = localStorage.getItem("token");
    if(token==undefined || token === "" || token == null){
      return false;
    }
    else{
      if(localStorage.getItem("roles")?.includes("ADMIN"))
        return true;
      else
        return false;
    }
  }

  findProviderCode(provider_code:string){    
    return this.http.post(`${this.authUrl}/findProviderCode`,provider_code);
  }

  findEmail(email: string){    
    return this.http.post(`${this.authUrl}/findEmail`,email);
  }

  findPayerCode(payer_code:string){    
    return this.http.post(`${this.authUrl}/findPayerCode`,payer_code);
  }

  findCurrentUser(){
    return this.http.get(`${this.authUrl}/current-user`, {headers: new HttpHeaders({'Authorization': 'Bearer ' + localStorage.getItem("token")})
    });
  }

  logout(){
    localStorage.setItem("token",'');
    if(localStorage.getItem("roles")?.includes("USER") || localStorage.getItem("roles")?.includes("PAYER")){
      localStorage.setItem("roles",'');
      window.location.href = "/login";
    }
    if(localStorage.getItem("roles")?.includes("ADMIN")){
      localStorage.setItem("roles",'');
      window.location.href = "/login";
    }
  }
}
