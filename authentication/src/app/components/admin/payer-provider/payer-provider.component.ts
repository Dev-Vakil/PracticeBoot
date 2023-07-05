import {LiveAnnouncer} from '@angular/cdk/a11y';
import { Component, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, NgControl, Validators } from '@angular/forms';
import {MatSort, Sort, MatSortModule} from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ProvidersService } from 'src/app/services/providers.service';

export interface PayerProvider {
  providerId: number;
  providerName: string;
  payerId: number;
  payerName:string;
  status: string;
}

interface ProviderList {
  providerId: number;
  providerName: string;
}

interface PayerList {
  payerId: number;
  payerName: string;
}

@Component({
  selector: 'app-payer-provider',
  templateUrl: './payer-provider.component.html',
  styleUrls: ['./payer-provider.component.css']
})
export class PayerProviderComponent {
  payerProviderForm!: FormGroup;  
  providerNames: ProviderList[] = [];
  payerNames: PayerList[] = [];
  status:boolean = false;

  displayedColumns: string[] = ['providerId', 'providerName','payerId', 'payerName', 'status'];
  dataSource !:MatTableDataSource<PayerProvider>;
  DATA: PayerProvider[] = [];
  constructor(private _liveAnnouncer: LiveAnnouncer, private formBuilder:FormBuilder, private providersService:ProvidersService){}

  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild('provider') provider!: number;  

  ngOnInit(){  
    this.payerProviderForm = this.formBuilder.group({ 
      providerId : ['',[Validators.required]],
      payerId : ['', Validators.required],
     })
    
    this.providersService.allProviders().subscribe(
      (response:any)=>{          
        let p:any;
        for(p in response){
          this.providerNames.push({'providerId':response[p].providerId,'providerName':response[p].providerName});          
        }
      },
      (error:any)=>{
        console.log(error);
      }      
    );
    this.providersService.allPayers().subscribe(
      (response:any)=>{          
        let p:any;
        for(p in response){
          this.payerNames.push({'payerId':response[p].payerId,'payerName':response[p].payerName});          
        }
      },
      (error:any)=>{
        console.log(error);
      }      
    );       
  }

  ngAfterViewInit() {
    this.providersService.getPayerProviders().subscribe(
      (response:any)=>{
        let p :any;
        for(p in response){
          this.DATA.push({'providerId':response[p].payerProviderId.provider.providerId,'providerName':response[p].payerProviderId.provider.providerName,'payerId':response[p].payerProviderId.payer.payerId,'payerName':response[p].payerProviderId.payer.payerName,status:response[p].status});
        }
        this.dataSource = new MatTableDataSource(this.DATA);
        this.dataSource.sort = this.sort;
      },
      (error:any)=>{
        console.log(error);        
      }
    );
  }

  changeStatus(status: boolean){
    this.status = status;
  }

  onSubmit(form:FormGroup){
    const providerId = form.get('providerId');
    const payerId = form.get('payerId');
    const status = this.status;

    var cred = {
      provider : providerId?.value,
      payer : payerId?.value,
      status : status
    }
    
    this.providersService.savePayerProviders(cred).subscribe(
      (response:any)=>{
        console.log(response);        
        window.location.href="/admin/payer-provider"; 
      },
      (error:any)=>{
        console.log(error);        
      }
    )
    
  }
  
 selectionChange(providerId : number,payerId:number){
    if(providerId && payerId){  
      this.providersService.getPayerProviderStatus(providerId,payerId).subscribe(
        (response:any)=>{
          this.status = response;            
        },
        (error:any)=>{
          console.log(error);        
        }
      );
    }
  }

  announceSortChange(sortState: any) {
    // This example uses English messages. If your application supports
    // multiple language, you would internationalize these strings.
    // Furthermore, you can customize the message to add additional
    // details about the values being sorted.
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }
}
