import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AuthenticationService } from './authentication.service';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';

export const adminGuard: CanActivateFn = (route, state) => {
  return true;
};

@Injectable({
  providedIn: 'root'
})

export class AdminGuard {
  
  constructor(private authService:AuthenticationService, private router:Router){}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
      let roles:any = localStorage.getItem("roles");
      let role:string;

      if(roles.includes('ADMIN')){
        return true;
      }
      else{
        this.router.navigate(['/admin/register'])
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