import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  url="http://localhost:8081"

  constructor(private http:HttpClient) { }

  register(credentials:object){
      return this.http.post(`${this.url}/providers/register`,credentials);
  }
}
