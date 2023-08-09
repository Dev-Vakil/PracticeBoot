import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadPricelistComponent } from './upload-pricelist.component';

describe('UploadPricelistComponent', () => {
  let component: UploadPricelistComponent;
  let fixture: ComponentFixture<UploadPricelistComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UploadPricelistComponent]
    });
    fixture = TestBed.createComponent(UploadPricelistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
