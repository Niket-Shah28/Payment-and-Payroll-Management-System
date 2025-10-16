import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganizationDepartments } from './organization-departments';

describe('OrganizationDepartments', () => {
  let component: OrganizationDepartments;
  let fixture: ComponentFixture<OrganizationDepartments>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OrganizationDepartments]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrganizationDepartments);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
