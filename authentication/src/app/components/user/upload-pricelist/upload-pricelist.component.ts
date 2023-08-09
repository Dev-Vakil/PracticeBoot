import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Pricelist } from 'src/app/interfaces/pricelist';
import { PricelistService } from 'src/app/services/pricelist.service';
import { saveAs } from 'file-saver';
import Swal from 'sweetalert2';

interface PayerList {
  payerId: number;
  payerName: number;
}
@Component({
  selector: 'app-upload-pricelist',
  templateUrl: './upload-pricelist.component.html',
  styleUrls: ['./upload-pricelist.component.css']
})
export class UploadPricelistComponent {
  pricelistDownload!: FormGroup; 
  pricelistUpload!: FormGroup;
  payerNames: PayerList[] = [];
  pricelists!: Pricelist;
  payerId!:number;
  file!:File;
  color: string = "";

  constructor(private pricelistService:PricelistService, private formBuilder:FormBuilder){}
  ngOnInit(){
    this.pricelistDownload = this.formBuilder.group({       
      payerId : ['', Validators.required],
     })
     this.pricelistUpload = this.formBuilder.group({       
      file : ['', Validators.required],
     })
    this.pricelistService.allPricelist().subscribe(
      (response:any)=>{    
        let p:any;
        let mySet = new Set();  
        for(p in response){
          mySet.add(response[p].payerId)        
        }        
        for(p of mySet){          
          this.payerNames.push({'payerId':p,'payerName':p})
        }               
      },
      (error:any)=>{
        console.log(error);        
      }     
    )
  }
    
 selectionChange(payerId:number){
    this.payerId = payerId;    
 }

 download(form:any){
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
