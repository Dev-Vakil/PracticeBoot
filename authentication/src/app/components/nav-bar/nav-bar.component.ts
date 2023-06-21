import { Component } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent {
  loggedIn: Boolean = this.authService.isLoggedIn();
  constructor(private authService:AuthenticationService){}

  logout(){
    this.loggedIn = false;
    this.authService.logout();
  }
}
