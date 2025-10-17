import { TestBed } from '@angular/core/testing';

import { OrganizationEmployeeDesignationService } from './organization-employee-designation-service';

describe('OrganizationEmployeeDesignationService', () => {
  let service: OrganizationEmployeeDesignationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrganizationEmployeeDesignationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
