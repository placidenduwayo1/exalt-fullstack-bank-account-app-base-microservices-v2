import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuspendedAccountComponent } from './suspended-account.component';

describe('SuspendedAccountComponent', () => {
  let component: SuspendedAccountComponent;
  let fixture: ComponentFixture<SuspendedAccountComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SuspendedAccountComponent]
    });
    fixture = TestBed.createComponent(SuspendedAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
