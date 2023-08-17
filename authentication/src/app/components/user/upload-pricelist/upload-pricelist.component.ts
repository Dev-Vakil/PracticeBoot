import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Pricelist } from 'src/app/interfaces/pricelist';
import { PricelistService } from 'src/app/services/pricelist.service';
import { saveAs } from 'file-saver';
import Swal from 'sweetalert2';
import { SelectPayerIdModalComponent } from '../../modals/select-payer-id-modal/select-payer-id-modal.component';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-upload-pricelist',
  templateUrl: './upload-pricelist.component.html',
  styleUrls: ['./upload-pricelist.component.css']
})
export class UploadPricelistComponent {
  pricelistUpload!: FormGroup;
  pricelists!: Pricelist;
  file!:File;
  color: string = "";
  errorDescription!:string;
  downloadError!:string;
  payerName!:string;
  payerId!: number ;
  
  constructor(private pricelistService:PricelistService, private formBuilder:FormBuilder, private activatedRoute: ActivatedRoute){
    this.payerId = history.state[0];        
  }

  ngOnInit(){
    this.pricelistUpload = this.formBuilder.group({       
     file : ['', Validators.required]
    })
  }
  
 downloadSample(){
  this.downloadError = "";
  this.pricelistService.downloadServicePricelistSample(this.payerId).subscribe(   
    (response:any)=>{
      if(response){                   
        saveAs(response,"Sample_"+this.payerId+"_Available_Service_List.xlsx")                      
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          title: 'File Downloaded', 
          showConfirmButton: false,
          timer: 1000
        })
      }
      else{
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          title: 'File Not Downloaded',
          showConfirmButton: false,
          timer: 1000
        })
      }
    }
  )
 }

 download(){ 
  
    this.pricelistService.downloadServicePricelist(this.payerId).subscribe(            
      (response:any)=>{
        // const reader = new FileReader();
        // reader.onloadend = () => {
        //   var base64data = reader.result;                
        //       console.log(base64data);
        // }
        // reader.readAsDataURL(response); 
        // console.log(reader);

        if(response){
          this.downloadError = "";
          saveAs(response,"payerID_"+this.payerId+"_Available_Service_List.xlsx")                      
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'File Downloaded', 
            showConfirmButton: false,
            timer: 1000
          })
        }
        else{
          this.downloadError = "File Not Found"
          Swal.fire({
            position: 'top-end',
            icon: 'error',
            title: 'File Not Downloaded',
            showConfirmButton: false,
            timer: 1000
          })
        }
      },
      (error:any)=>{
        console.log(error);        
        this.downloadError = "File Not Found. Try downloading the sample file";
      }
    )
 }

 selectFile(event: any) {
  let reader = new FileReader();
  // when the load event is fired and the file not empty
  if(event.target.files && event.target.files.length > 0) {
    this.file = event.target.files[0];
  }
 }
 fileUpload(form:any){
  console.log(form.get('file'))
  const formData = new FormData();
  formData.append('file',this.file);
  formData.append('payerId',String(this.payerId));
   this.pricelistService.uploadServicePricelist(formData).subscribe(
      (response:any)=>{
        this.errorDescription = ""; 
        if(response == true){
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'File Uploaded',
            showConfirmButton: false,
            timer: 1000
          })          
        }
        else{
          Swal.fire({
            position: 'top-end',
            icon: 'error',
            title: 'File Not Uploaded',
            showConfirmButton: false,
            timer: 1000
          })
        }
      },
      (error:any)=>{       
        this.errorDescription = error.error.error_description;
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          title: 'Error File Not Uploaded',
          showConfirmButton: false,
          timer: 1000
        })
      }
    );
 }
}
