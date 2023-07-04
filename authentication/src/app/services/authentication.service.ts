import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  authUrl="http://localhost:8080/auth"  

  constructor(private http:HttpClient) { }

  registerProvider(credentials:object){
      return this.http.post(`${this.authUrl}/provider/register`,credentials);
  }
  registerPayer(credentials:object){
    return this.http.post(`${this.authUrl}/payer/register`,credentials);
}
  
  login(credentials:object){
      return this.http.post(`${this.authUrl}/login`,credentials);
  }

  saveToken(token:string,roles:any){
    localStorage.setItem("token",token);    
    console.log(roles);
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

  findProviderEmail(email: string){    
    return this.http.post(`${this.authUrl}/findProviderEmail`,email);
  }

  findPayerCode(payer_code:string){    
    return this.http.post(`${this.authUrl}/findPayerCode`,payer_code);
  }

  findPayerEmail(email: string){    
    return this.http.post(`${this.authUrl}/findPayerEmail`,email);
  }

  logout(){
    localStorage.setItem("token",'');
    if(localStorage.getItem("roles")?.includes("USER") || localStorage.getItem("roles")?.includes("PAYER")){
      localStorage.setItem("roles",'');
      window.location.href = "/user/login";
    }
    if(localStorage.getItem("roles")?.includes("ADMIN")){
      localStorage.setItem("roles",'');
      window.location.href = "/admin/login";
    }
  }
}
