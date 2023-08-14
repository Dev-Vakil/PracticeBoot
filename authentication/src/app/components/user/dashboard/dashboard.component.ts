import { Component } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  username!:string;

  constructor(private authService:AuthenticationService){}

  ngOnInit(){
    this.authService.findCurrentUser().subscribe(
      (response:any)=>{        
        this.username = response.principal.username;
      }
    )
  }
}
