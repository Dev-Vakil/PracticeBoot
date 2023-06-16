import { Component} from '@angular/core';
import { FormGroup, FormControl, FormBuilder, NgForm, Validators } from '@angular/forms';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm!: FormGroup;  
  // email:string = '';
  constructor( private formBuilder:FormBuilder, private authService:AuthenticationService){  }
  
  ngOnInit(){   
    this.registerForm = this.formBuilder.group({
      providerId:['',Validators.required],
      providerName:['',Validators.required],
      providerCode:['',Validators.required],
      userName:['',Validators.required],
      email:['', [Validators.required, Validators.pattern("[a-z0-9._%+-]+@[a-z0-9.-]+[.][a-z]{2,}$")]],
      password:['',[Validators.required, Validators.pattern("(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}")]]  
    })
  }

  registerData(form:FormGroup){    
    const username = form.get('userName');
    const email = form.get('email');
    const password = form.get('password');
    var cred = {
      username: username?.value,
      email: email?.value,
      password: password?.value
    }
    console.log(username?.value,email?.value,password?.value);
    this.authService.register(cred).subscribe(
      response=>{
        console.log(response);
        
      },
      error=>{
        console.log(error);       
      }
    )
  }
}
