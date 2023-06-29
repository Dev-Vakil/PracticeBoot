import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router, CanActivateFn } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticationService } from './authentication.service';

export const authGuard: CanActivateFn = (route, state) => {
  return true;
};

@Injectable({
  providedIn: 'root'
})
export class AuthGuard {
  
  constructor(private authService:AuthenticationService, private router:Router){}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    if (this.authService.isLoggedIn()) {     
    //   let role = localStorage.getItem("role");
    //   if(role === 'ADMIN'){
    //     this.router.navigate(['']);    
    //   }
    //   else{
    //     this.router.navigate(['login']);    
    //   }
      return true;
    }        
    this.router.navigate(['login']);    
    return false;
  }


}