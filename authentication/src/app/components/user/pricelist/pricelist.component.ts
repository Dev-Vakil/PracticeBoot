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
  selector: 'app-pricelist',
  templateUrl: './pricelist.component.html',
  styleUrls: ['./pricelist.component.css']
})
export class PricelistComponent {
  pricelistDownload!: FormGroup; 
  pricelistUpload!: FormGroup;
  payerNames: PayerList[] = [];
  pricelists!: Pricelist;
  payerId!:number;
  file!:File;

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
        for(p in response){
          this.payerNames.push({'payerId':response[p].payerId,'payerName':response[p].payerId})                    
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

//  downloadFile(data: Response) {
//   const blob = new Blob([data], { type: 'application/octet-stream' });
//   const url= window.URL.createObjectURL(blob);
//   window.open(url);
// }

 download(form:any){
    this.pricelistService.downloadServicePricelist(this.payerId).subscribe(            
      (response:any)=>{
        // const reader = new FileReader();
        // reader.onloadend = () => {
        //   var base64data = reader.result;                
        //       console.log(base64data);
        // }
        // reader.readAsDataURL(response); 
        // console.log(response.headers.ge);

        if(response){                   
          saveAs(response,"Available_Service_List.xlsx")                      
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
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          title: 'File Uploaded',
          showConfirmButton: false,
          timer: 1000
        })          
      },
      (error:any)=>{
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          title: 'File Not Uploaded',
          showConfirmButton: false,
          timer: 1000
        })
      }
    );
 }
}
