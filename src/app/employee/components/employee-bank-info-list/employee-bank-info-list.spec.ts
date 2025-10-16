import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeBankInfoList } from './employee-bank-info-list';

describe('EmployeeBankInfoList', () => {
  let component: EmployeeBankInfoList;
  let fixture: ComponentFixture<EmployeeBankInfoList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EmployeeBankInfoList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmployeeBankInfoList);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
