import { TestBed } from '@angular/core/testing';

import { OrganizationDepartmentService } from './organization-department-service';

describe('OrganizationDepartmentService', () => {
  let service: OrganizationDepartmentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrganizationDepartmentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
