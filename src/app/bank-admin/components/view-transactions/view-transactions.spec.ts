import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewTransactions } from './view-transactions';

describe('ViewTransactions', () => {
  let component: ViewTransactions;
  let fixture: ComponentFixture<ViewTransactions>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViewTransactions]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewTransactions);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
