import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServicePricelistModalComponent } from './service-pricelist-modal.component';

describe('ServicePricelistModalComponent', () => {
  let component: ServicePricelistModalComponent;
  let fixture: ComponentFixture<ServicePricelistModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ServicePricelistModalComponent]
    });
    fixture = TestBed.createComponent(ServicePricelistModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
