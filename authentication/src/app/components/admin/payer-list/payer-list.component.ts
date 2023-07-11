import {LiveAnnouncer} from '@angular/cdk/a11y';
import { Component, AfterViewInit, ViewChild } from '@angular/core';
import { MatFormFieldDefaultOptions } from '@angular/material/form-field';
import {MatSort, Sort, MatSortModule} from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Payer } from 'src/app/interfaces/payer';
import { ProvidersService } from 'src/app/services/providers.service';

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
        console.log(response);
        
        this.ELEMENT_DATA = response;
        this.dataSource = new MatTableDataSource(this.ELEMENT_DATA);
        this.dataSource.sort = this.sort;
      }
    );   
  }
   
  
  announceSortChange(sortState: any) {
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }  
}
