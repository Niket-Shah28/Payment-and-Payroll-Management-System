import { TestBed } from '@angular/core/testing';

import { OrganizationCoreDataService } from './organization-core-data-service';

describe('OrganizationCoreDataService', () => {
  let service: OrganizationCoreDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrganizationCoreDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
