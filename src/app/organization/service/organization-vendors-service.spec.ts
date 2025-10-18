import { TestBed } from '@angular/core/testing';

import { OrganizationVendorsService } from './organization-vendors-service';

describe('OrganizationVendorsService', () => {
  let service: OrganizationVendorsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrganizationVendorsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
