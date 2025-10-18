import { TestBed } from '@angular/core/testing';

import { ApproveOrganizationService } from './approve-organization-service';

describe('ApproveOrganizationService', () => {
  let service: ApproveOrganizationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApproveOrganizationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
