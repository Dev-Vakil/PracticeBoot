import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ProvidersService {

  AdminUrl="http://localhost:8083/admin"  
  UserUrl="http://localhost:8083/user"

  constructor(private http:HttpClient) { }

  allProviders(search:string){
    let queryParams = new HttpParams().append("providerFilter",search);
    return this.http.get(`${this.AdminUrl}/providers`, {params: queryParams, headers: new HttpHeaders({'Authorization': 'Bearer ' + localStorage.getItem("token")})
    });
  }

  allPayers(search:string){
    let queryParams = new HttpParams().append("payerFilter",search);
    return this.http.get(`${this.AdminUrl}/payers`, {params: queryParams, headers: new HttpHeaders({'Authorization': 'Bearer ' + localStorage.getItem("token")})
    });
  }

  savePayerProviders(credentials:object){  
    return this.http.post(`${this.AdminUrl}/payerProvider`,credentials,{ headers: new HttpHeaders({'Authorization': 'Bearer ' + localStorage.getItem("token")})
    });
  }

  getPayerProviders(){
    return this.http.get(`${this.AdminUrl}/payerProvider`, { headers: new HttpHeaders({'Authorization': 'Bearer ' + localStorage.getItem("token")})
    });
  }

  getPayerProviderStatus(providerId:number, payerId:number){
    let queryParams = new HttpParams().append("providerId",providerId);
    let queryParams2 = queryParams.append("payerId",payerId);
    return this.http.get(`${this.AdminUrl}/payerProviderStatus`,{params: queryParams2, headers: new HttpHeaders({'Authorization': 'Bearer ' + localStorage.getItem("token")})
    });
  }  
  
  getAssociatedPayer(email:string,search:string){
    let queryParams = new HttpParams().append("email",email);
    let queryParams2 = queryParams.append("search",search);
    return this.http.get(`${this.UserUrl}/payers`,{params: queryParams2, headers: new HttpHeaders({'Authorization': 'Bearer ' + localStorage.getItem("token")})
    });
  }
}
