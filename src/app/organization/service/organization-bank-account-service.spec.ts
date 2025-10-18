import { TestBed } from '@angular/core/testing';

import { OrganizationBankAccountService } from './organization-bank-account-service';

describe('OrganizationBankAccountService', () => {
  let service: OrganizationBankAccountService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrganizationBankAccountService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
