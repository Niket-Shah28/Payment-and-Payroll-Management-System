import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApprovePayments } from './approve-payments';

describe('ApprovePayments', () => {
  let component: ApprovePayments;
  let fixture: ComponentFixture<ApprovePayments>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ApprovePayments]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ApprovePayments);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
