import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectPayerIdModalComponent } from './select-payer-id-modal.component';

describe('SelectPayerIdModalComponent', () => {
  let component: SelectPayerIdModalComponent;
  let fixture: ComponentFixture<SelectPayerIdModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SelectPayerIdModalComponent]
    });
    fixture = TestBed.createComponent(SelectPayerIdModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
