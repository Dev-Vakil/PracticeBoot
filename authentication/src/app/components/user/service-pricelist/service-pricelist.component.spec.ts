import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServicePricelistComponent } from './service-pricelist.component';

describe('ServicePricelistComponent', () => {
  let component: ServicePricelistComponent;
  let fixture: ComponentFixture<ServicePricelistComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ServicePricelistComponent]
    });
    fixture = TestBed.createComponent(ServicePricelistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
