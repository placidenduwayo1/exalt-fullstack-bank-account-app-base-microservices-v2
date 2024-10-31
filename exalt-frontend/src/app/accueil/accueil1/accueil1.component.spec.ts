import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Accueil1Component } from './accueil1.component';

describe('AccueilComponent', () => {
  let component: Accueil1Component;
  let fixture: ComponentFixture<Accueil1Component>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [Accueil1Component]
    });
    fixture = TestBed.createComponent(Accueil1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
