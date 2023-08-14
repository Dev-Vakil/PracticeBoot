import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ProvidersService } from 'src/app/services/providers.service';

interface PayerList {
  payerId: number;
  payerName: string;
}

@Component({
  selector: 'app-select-payer-id-modal',
  templateUrl: './select-payer-id-modal.component.html',
  styleUrls: ['./select-payer-id-modal.component.css']
})
export class SelectPayerIdModalComponent {
  associatedPayerForm!:FormGroup;
  payers:PayerList[] = [];
  selectedPayer!:PayerList;
  
  constructor(private authService:AuthenticationService, private providerService:ProvidersService, private formBuilder:FormBuilder, public dialogRef: MatDialogRef<SelectPayerIdModalComponent>,@Inject(MAT_DIALOG_DATA) public data:PayerList){ }

  ngOnInit(){
    this.associatedPayerForm = this.formBuilder.group({
      payerId: ['',Validators.required],
    }
    )

    this.authService.findCurrentUser().subscribe(
      (response:any)=>{
        this.providerService.getAssociatedPayer(response.principal.email,"").subscribe(
          (response:any)=>{    
            let p;
            for(p in response){
              this.payers.push({'payerId':response[p].payerId,'payerName':response[p].payerName});
            }                        
          }
        )
      },
      (error:any)=>{
        console.log(error);
        this.authService.logout();
      }      

    )
  }
  closeDialog(){
    // Write your stuff here
    this.dialogRef.close(); // <- Close the mat dialog
  }

  formSubmit(form:any){
    this.selectedPayer = form.get('payerId')?.value;
    this.dialogRef.close(this.selectedPayer);
    console.log(this.selectedPayer);      
  }

}
