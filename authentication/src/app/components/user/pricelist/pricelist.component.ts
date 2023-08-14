import { Component, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Pricelist } from 'src/app/interfaces/pricelist';
import { PricelistService } from 'src/app/services/pricelist.service';
import { PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Pageable } from 'src/app/interfaces/pageable';
import { MatDialog } from '@angular/material/dialog';
import { ServicePricelistModalComponent } from '../../modals/service-pricelist-modal/service-pricelist-modal.component';

@Component({
  selector: 'app-pricelist',
  templateUrl: './pricelist.component.html',
  styleUrls: ['./pricelist.component.css']
})
export class PricelistComponent {
  displayedColumns: string[] = ['pricelistId', 'providerId', 'payerId', 'status', 'uploadedBy','services'];
  dataSource !:MatTableDataSource<Pricelist>;
  ELEMENT_DATA!: Pricelist[];
  totalElements: number = 0;

  constructor(private _liveAnnouncer: LiveAnnouncer, private pricelistService:PricelistService, private dialog: MatDialog){
  }

  @ViewChild(MatSort) sort!: MatSort;

  openDialog(pricelistId:number,status:string){    
    this.dialog.open(ServicePricelistModalComponent, {
      data: {
        "pricelistId": pricelistId,
        "status": status
      }
    });
  }

  ngOnInit(){
    this.getPricelist({page: 0, size: 10});
  }

  private getPricelist(request:Pageable){
    this.pricelistService.getPricelist(request).subscribe(
      (response:any)=>{                    
        this.ELEMENT_DATA = response['content'];        
        this.totalElements = response['totalElements']      
        this.dataSource = new MatTableDataSource(this.ELEMENT_DATA);    
        this.dataSource.sort = this.sort;  
      }
    )
  }

  nextPage(event: PageEvent) {
    let page = event.pageIndex;    
    let size = event.pageSize;
    this.getPricelist({page,size});
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
