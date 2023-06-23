import { Component } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';


@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.css']
})
export class SideBarComponent {
  loggedIn: Boolean = this.authService.isLoggedIn();
  constructor(private authService:AuthenticationService){}
  events: string[] = [];
  opened!: boolean;
}
