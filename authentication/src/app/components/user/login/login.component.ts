import { Component, OnInit} from '@angular/core';
import { FormGroup, FormControl, FormBuilder, NgForm, Validators } from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  loginForm!: FormGroup;  
  loginError!: Boolean;
  constructor( private formBuilder:FormBuilder, private authService:AuthenticationService){ }
  ngOnInit(){   
    this.loginForm = this.formBuilder.group({
      email:['', [Validators.required, Validators.pattern("[a-z0-9._%+-]+@[a-z0-9.-]+[.][a-z]{2,}$")]],
      password:['',[Validators.required]],
      userType:['', Validators.required]
    })
  }

  PostData(form:FormGroup){        
    const email = form.get('email');
    const password = form.get('password');
    const userType = form.get('userType');
    console.log(userType);
    var cred = {      
      email: email?.value,
      password: password?.value,
      role: userType?.value
    }
    this.authService.login(cred).subscribe(
      (response:any)=>{                       
        this.authService.saveToken(response.token,response.roles);
        window.location.href="/user/dashboard";                
      },
      (error:any)=>{
          console.log(error.status);          
          this.loginError = true;
      }
    )
  }
}
