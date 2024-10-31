import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuspendedListComponent } from './suspended-list.component';

describe('SuspendedListComponent', () => {
  let component: SuspendedListComponent;
  let fixture: ComponentFixture<SuspendedListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SuspendedListComponent]
    });
    fixture = TestBed.createComponent(SuspendedListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
