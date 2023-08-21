import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PayerPricelistComponent } from './payer-pricelist.component';

describe('PayerPricelistComponent', () => {
  let component: PayerPricelistComponent;
  let fixture: ComponentFixture<PayerPricelistComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PayerPricelistComponent]
    });
    fixture = TestBed.createComponent(PayerPricelistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
