import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WithdrawalOperationComponent } from './withdrawal-operation.component';

describe('WithdrawalOperationComponent', () => {
  let component: WithdrawalOperationComponent;
  let fixture: ComponentFixture<WithdrawalOperationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WithdrawalOperationComponent]
    });
    fixture = TestBed.createComponent(WithdrawalOperationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
