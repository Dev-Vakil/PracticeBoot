import { ErrorHandler, Injectable } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class GlobalErrorHandlerService implements ErrorHandler{
  constructor(private authService:AuthenticationService) { }

  handleError(error: any): void {
    console.error('An error occurred:', error.message);
    if(error.status == 401 || error.status == 403){
      this.authService.logout();
    }
  }

}
