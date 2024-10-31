import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArchivedCustomerComponent } from './archived-customer.component';

describe('ArchivedCustomerComponent', () => {
  let component: ArchivedCustomerComponent;
  let fixture: ComponentFixture<ArchivedCustomerComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ArchivedCustomerComponent]
    });
    fixture = TestBed.createComponent(ArchivedCustomerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
