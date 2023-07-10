import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssociatedPayersComponent } from './associated-payers.component';

describe('AssociatedPayersComponent', () => {
  let component: AssociatedPayersComponent;
  let fixture: ComponentFixture<AssociatedPayersComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AssociatedPayersComponent]
    });
    fixture = TestBed.createComponent(AssociatedPayersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
