import { Component} from '@angular/core';
import { FormGroup, FormControl, FormBuilder, NgForm, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm!: FormGroup;  
  
  constructor( private formBuilder:FormBuilder){  }
  
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
   
  }
}
