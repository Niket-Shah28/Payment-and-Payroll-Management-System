import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganizationEmployeeDesignations } from './organization-employee-designations';

describe('OrganizationEmployeeDesignations', () => {
  let component: OrganizationEmployeeDesignations;
  let fixture: ComponentFixture<OrganizationEmployeeDesignations>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OrganizationEmployeeDesignations]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrganizationEmployeeDesignations);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
