import { TestBed } from '@angular/core/testing';

import { ViewOrganizationDocsService } from './view-organization-docs-service';

describe('ViewOrganizationDocs', () => {
  let service: ViewOrganizationDocsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ViewOrganizationDocsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
