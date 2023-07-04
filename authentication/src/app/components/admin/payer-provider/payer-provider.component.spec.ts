import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PayerProviderComponent } from './payer-provider.component';

describe('PayerProviderComponent', () => {
  let component: PayerProviderComponent;
  let fixture: ComponentFixture<PayerProviderComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PayerProviderComponent]
    });
    fixture = TestBed.createComponent(PayerProviderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
