import { Component, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { PricelistService } from 'src/app/services/pricelist.service';
import { PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Pageable } from 'src/app/interfaces/pageable';
import { ServicePricelist } from 'src/app/interfaces/service-pricelist';

@Component({
  selector: 'app-service-pricelist',
  templateUrl: './service-pricelist.component.html',
  styleUrls: ['./service-pricelist.component.css']
})
export class ServicePricelistComponent {
  displayedColumns: string[] = ['servicePricelistId', 'serviceCode', 'serviceDescription', 'price', 'status', 'pricelistId'];
  dataSource !:MatTableDataSource<ServicePricelist>;
  ELEMENT_DATA!: ServicePricelist[];
  totalElements: number = 0;

  constructor(private _liveAnnouncer: LiveAnnouncer, private pricelistService:PricelistService){}

  @ViewChild(MatSort) sort!: MatSort;

  ngOnInit(){
    this.getServicePricelist({page: 0,size: 2})
  }

  private getServicePricelist(request:Pageable){
    this.pricelistService.getServicePricelist(request).subscribe(
      (response:any)=>{        
        this.ELEMENT_DATA = response['content'];
        this.totalElements = response['totalElements'];
        this.dataSource = new MatTableDataSource(this.ELEMENT_DATA);    
        this.dataSource.sort = this.sort;   
      }
    )
  }

  nextPage(event: PageEvent){
    let page = event.pageIndex;
    let size = event.pageSize;
    this.getServicePricelist({page,size});
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
