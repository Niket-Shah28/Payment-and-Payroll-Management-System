import { TestBed } from '@angular/core/testing';

import { OrganizationPayoutService } from './organization-payout-service';

describe('OrganizationPayoutService', () => {
  let service: OrganizationPayoutService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrganizationPayoutService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
