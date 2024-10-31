import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DepositOperationComponent } from './deposit-operation.component';

describe('DepositOperationComponent', () => {
  let component: DepositOperationComponent;
  let fixture: ComponentFixture<DepositOperationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DepositOperationComponent]
    });
    fixture = TestBed.createComponent(DepositOperationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
