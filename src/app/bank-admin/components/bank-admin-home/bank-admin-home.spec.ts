import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BankAdminHome } from './bank-admin-home';

describe('BankAdminHome', () => {
  let component: BankAdminHome;
  let fixture: ComponentFixture<BankAdminHome>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BankAdminHome]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BankAdminHome);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
