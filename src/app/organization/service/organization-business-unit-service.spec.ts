import { TestBed } from '@angular/core/testing';

import { OrganizationBusinessUnitService } from './organization-business-unit-service';

describe('OrganizationBusinessUnitService', () => {
  let service: OrganizationBusinessUnitService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrganizationBusinessUnitService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
