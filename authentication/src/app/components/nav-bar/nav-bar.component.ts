import { Component } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent {
  loggedIn = false;
  constructor(private authService:AuthenticationService){}
  
  logout(){
    this.authService.logout();
  }
}
