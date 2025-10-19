import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganizationPayoutComponent } from './organization-payout-component';

describe('OrganizationPayoutComponent', () => {
  let component: OrganizationPayoutComponent;
  let fixture: ComponentFixture<OrganizationPayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OrganizationPayoutComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrganizationPayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
