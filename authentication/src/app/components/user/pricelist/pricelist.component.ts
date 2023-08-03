import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Pricelist } from 'src/app/interfaces/pricelist';
import { PricelistService } from 'src/app/services/pricelist.service';

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
  payerNames: PayerList[] = [];
  pricelists!: Pricelist;
  payerId!:number;

  constructor(private pricelistService:PricelistService, private formBuilder:FormBuilder){}
  ngOnInit(){
    this.pricelistDownload = this.formBuilder.group({       
      payerId : ['', Validators.required],
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

 download(form:any){
    this.pricelistService.downloadServicePricelist(this.payerId).subscribe(
      (response:any)=>{
        if(response == true){
          alert("Downloaded")
        }
        else{
          alert("Errror")
        }
      }
    )
 }


}
