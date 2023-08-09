import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, ViewChild } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Payer } from 'src/app/interfaces/payer';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ProvidersService } from 'src/app/services/providers.service';

@Component({
  selector: 'app-associated-payers',
  templateUrl: './associated-payers.component.html',
  styleUrls: ['./associated-payers.component.css']
})
export class AssociatedPayersComponent {
  displayedColumns: string[] = ['payerId', 'payerName', 'payerCode', 'email'];
  dataSource !:MatTableDataSource<Payer>;
  ELEMENT_DATA!: Payer[];
  email:string = ""
  constructor(private _liveAnnouncer: LiveAnnouncer, public providersService:ProvidersService, public authService:AuthenticationService) {   
  } 

  @ViewChild(MatSort) sort!: MatSort;

  ngAfterViewInit() {
    this.authService.findCurrentUser().subscribe(
      (response:any)=>{            
        this.email = response.principal.email;  
        this.onSearch("");        
      },
      (error:any)=>{
        console.log(error);        
      }
    );    
  }
  onSearch(search:string){        
    this.providersService.getAssociatedPayer(this.email,search).subscribe(
      (response:any)=>{
        this.ELEMENT_DATA = response;
        this.dataSource = new MatTableDataSource(this.ELEMENT_DATA);
        this.dataSource.sort = this.sort;
      }
    );
  }
   /** Announce the change in sort state for assistive technology. */
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
