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
  providerCodeError!: Boolean;
  emailError!: Boolean;
  constructor( private formBuilder:FormBuilder, private authService:AuthenticationService){  }
  
  ngOnInit(){   
    this.registerForm = this.formBuilder.group({      
      providerName:['',Validators.required],
      providerCode:['',Validators.required],
      userName:['',Validators.required],
      email:['', [Validators.required, Validators.pattern("[a-z0-9._%+-]+@[a-z0-9.-]+[.][a-z]{2,}$")]],
      password:['',[Validators.required, Validators.pattern(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/)]]  
    })
  }

  registerData(form:FormGroup){    
    const providerName = form.get('providerName');
    const providerCode = form.get('providerCode');    
    const username = form.get('userName');
    const email = form.get('email');
    const password = form.get('password');
    var cred = {
      providerName: providerName?.value,
      providerCode: providerCode?.value,
      username: username?.value,
      email: email?.value,
      password: password?.value
    }

    this.authService.findProviderCode(providerCode?.value).subscribe(
      (response:any)=>{                     
        if(response == false){              
          this.providerCodeError = false;
        }
        else{          
          this.providerCodeError = true;
        }        
      },
      (error:any)=>{
        this.providerCodeError = true;        
      }
    );    
    this.authService.findEmail(email?.value).subscribe(      
      (response:any)=>{        
        if(response == null){
          this.emailError = false;
        }
        else{
          this.emailError = true;
        }       
      },
      (error:any)=>{
        this.emailError = true;
      }
    )
   
    if(this.providerCodeError == false && this.emailError == false){              
      this.authService.register(cred).subscribe(
        (response:any)=>{
          console.log(response);
          window.location.href="/login";    
        },
        (error:any)=>{
          console.log(error.status);  
        }
      )
    }
    
  }
}
