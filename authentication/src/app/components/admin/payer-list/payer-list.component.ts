import {LiveAnnouncer} from '@angular/cdk/a11y';
import { Component, AfterViewInit, ViewChild } from '@angular/core';
import { MatFormFieldDefaultOptions } from '@angular/material/form-field';
import {MatSort, Sort, MatSortModule} from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ProvidersService } from 'src/app/services/providers.service';

export interface Payer {
  payerId: number;
  payerName: string;
  payerCode: string;  
  password: string;
  email: string;
  isActive: boolean;
  createdAt:string;
  modifiedAt:string;
  roleAssociation: number[];
}

@Component({
  selector: 'app-payer-list',
  templateUrl: './payer-list.component.html',
  styleUrls: ['./payer-list.component.css']
})

export class PayerListComponent {
  displayedColumns: string[] = ['payerId', 'payerName', 'payerCode', 'email'];
  dataSource !:MatTableDataSource<Payer>;
  ELEMENT_DATA!: Payer[];
  
  constructor(private _liveAnnouncer: LiveAnnouncer, public providersService:ProvidersService) {   
  } 

  @ViewChild(MatSort) sort!: MatSort;

  ngAfterViewInit() {
    this.onSearch("");
  }

  onSearch(search:string){        
    this.providersService.allPayers(search).subscribe(
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
