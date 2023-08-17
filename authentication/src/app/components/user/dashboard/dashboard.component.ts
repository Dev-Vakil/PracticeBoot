import { Component, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  username!:string;
  payerId!: number;

  constructor(private authService:AuthenticationService){ 
  }

  ngOnInit(){    
    this.authService.findCurrentUser().subscribe(
      (response:any)=>{        
        this.username = response.principal.username;
      }
    )
  }  
}
