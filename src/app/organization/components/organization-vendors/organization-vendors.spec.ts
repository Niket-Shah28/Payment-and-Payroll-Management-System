import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganizationVendors } from './organization-vendors';

describe('OrganizationVendors', () => {
  let component: OrganizationVendors;
  let fixture: ComponentFixture<OrganizationVendors>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OrganizationVendors]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrganizationVendors);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
