import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PayerListComponent } from './payer-list.component';

describe('PayerListComponent', () => {
  let component: PayerListComponent;
  let fixture: ComponentFixture<PayerListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PayerListComponent]
    });
    fixture = TestBed.createComponent(PayerListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
