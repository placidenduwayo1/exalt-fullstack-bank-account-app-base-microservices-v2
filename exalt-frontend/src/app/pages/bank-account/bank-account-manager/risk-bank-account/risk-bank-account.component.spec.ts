import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RiskBankAccountComponent } from './risk-bank-account.component';

describe('RiskBankAccountComponent', () => {
  let component: RiskBankAccountComponent;
  let fixture: ComponentFixture<RiskBankAccountComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RiskBankAccountComponent]
    });
    fixture = TestBed.createComponent(RiskBankAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
