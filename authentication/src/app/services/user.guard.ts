import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AuthenticationService } from './authentication.service';
import { Observable } from 'rxjs';

export const userGuard: CanActivateFn = (route, state) => {
  return true;
};

@Injectable({
  providedIn: 'root'
})

export class UserGuard {
  
  constructor(private authService:AuthenticationService, private router:Router){}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
      let roles:any = localStorage.getItem("roles");
      let role:string;

      if(roles.includes('USER') || roles.includes('PAYER')){
        return true;
      }
      else{
        this.router.navigate(['/user/login'])
        return false;
      }

      // for(role in  roles){
      //   if(role == "ADMIN"){
      //     return true;
      //   }
      // }
      // this.router.navigate(['/admin/login']);    
      // return false;
    }        

  }
