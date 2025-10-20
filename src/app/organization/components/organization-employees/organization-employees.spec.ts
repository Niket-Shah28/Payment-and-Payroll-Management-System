import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganizationEmployees } from './organization-employees';

describe('OrganizationEmployees', () => {
  let component: OrganizationEmployees;
  let fixture: ComponentFixture<OrganizationEmployees>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OrganizationEmployees]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrganizationEmployees);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
