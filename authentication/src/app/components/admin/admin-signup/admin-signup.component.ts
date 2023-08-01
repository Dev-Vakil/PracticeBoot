import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-admin-signup',
  templateUrl: './admin-signup.component.html',
  styleUrls: ['./admin-signup.component.css']
})
export class AdminSignupComponent {
  userType:string = "1";
  providerForm!: FormGroup;
  payerForm!: FormGroup;
  providerCodeError!: Boolean;
  providerEmailError!: Boolean;
  payerCodeError!: Boolean;
  payerEmailError!: Boolean;
  constructor( private formBuilder:FormBuilder, private authService:AuthenticationService){  }
 
  ngOnInit(){   
      this.providerForm = this.formBuilder.group({      
      providerName:['',Validators.required],
      providerCode:['',Validators.required],
      userName:['',Validators.required],
      email:['', [Validators.required, Validators.pattern("[a-z0-9._%+-]+@[a-z0-9.-]+[.][a-z]{2,}$")]],
      password:['',[Validators.required, Validators.pattern(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/)]]  
    })
      this.payerForm = this.formBuilder.group({      
      payerName:['',Validators.required],
      payerCode:['',Validators.required],      
      email:['', [Validators.required, Validators.pattern("[a-z0-9._%+-]+@[a-z0-9.-]+[.][a-z]{2,}$")]],
      password:['',[Validators.required, Validators.pattern(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/)]]  
    })
  }

  registerProvider(form:FormGroup){        
    const providerName = form.get('providerName');
    const providerCode = form.get('providerCode');    
    const username = form.get('userName');
    const email = form.get('email');
    const password = form.get('password');
    var cred = {
      name: providerName?.value,
      code: providerCode?.value,
      username: username?.value,
      email: email?.value,
      password: password?.value,
      userType: "PROVIDER"
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
        if(response == false){
          this.providerEmailError = false;
        }
        else{
          this.providerEmailError = true;
        }       
      },
      (error:any)=>{
        this.providerEmailError = true;
      }
    )
   
    if(this.providerCodeError == false && this.providerEmailError == false){              
      this.authService.register(cred).subscribe(
        (response:any)=>{
          console.log(response);
          window.location.href="/login";    
        },
        (error:any)=>{
          window.location.href="/login";  
          console.log(error.status);  
        }
      )
    }
    
  }

  registerPayer(form:FormGroup){
    const payerName = form.get('payerName');
    const payerCode = form.get('payerCode');    
    const email = form.get('email');
    const password = form.get('password');
    var cred = {
      name: payerName?.value,
      code: payerCode?.value,      
      email: email?.value,
      password: password?.value,
      userType: "PAYER"
    }

    this.authService.findPayerCode(payerCode?.value).subscribe(
      (response:any)=>{                     
        if(response == false){              
          this.payerCodeError = false;
        }
        else{          
          this.payerCodeError = true;
        }        
      },
      (error:any)=>{
        this.payerCodeError = true;        
      }
    );    
    this.authService.findEmail(email?.value).subscribe(      
      (response:any)=>{        
        if(response == false){
          this.payerEmailError = false;
        }
        else{
          this.payerEmailError = true;
        }       
      },
      (error:any)=>{
        this.payerEmailError = true;
      }
    )
   
    if(this.payerCodeError == false && this.payerEmailError == false){              
      this.authService.register(cred).subscribe(
        (response:any)=>{
          window.location.href="/login";    
          console.log(response);
        },
        (error:any)=>{
          window.location.href="/login";  
          console.log(error.status);  
        }
      )
    }
    
  }
}
