import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Accueil3Component } from './accueil3.component';

describe('Accueil3Component', () => {
  let component: Accueil3Component;
  let fixture: ComponentFixture<Accueil3Component>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [Accueil3Component]
    });
    fixture = TestBed.createComponent(Accueil3Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
