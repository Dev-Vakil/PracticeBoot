import {LiveAnnouncer} from '@angular/cdk/a11y';
import { Component, AfterViewInit, ViewChild } from '@angular/core';
import {MatSort, Sort, MatSortModule} from '@angular/material/sort';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import { ProvidersService } from 'src/app/services/providers.service';

export interface PeriodicElement {
  providerId: number;
  providerName: string;
  providerCode: string;
  username: string;
  password: string;
  email: string;
  isActive: boolean;
  createdAt:string;
  modifiedAt:string;
  roleAssociation: number[];
}


// providerId: 1, providerName: "dev", providerCode: "23", username: "dev", password: "sds", email: "dwdas", isActive: true, createdAt: "54:00", modifiedAt: "12:43", roleAssociation: [1,2]
@Component({
  selector: 'app-provider-list',
  templateUrl: './provider-list.component.html',
  styleUrls: ['./provider-list.component.css']
})
export class ProviderListComponent {
  displayedColumns: string[] = ['providerId', 'providerName', 'providerCode', 'username'];
  dataSource !:MatTableDataSource<PeriodicElement>;
  ELEMENT_DATA!: PeriodicElement[];
  
  
  constructor(private _liveAnnouncer: LiveAnnouncer, public providersService:ProvidersService) {   
  }

  @ViewChild(MatSort) sort!: MatSort;

  ngAfterViewInit() {
    this.providersService.allProviders().subscribe(
      (response:any)=>{        
        console.log(response);
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
