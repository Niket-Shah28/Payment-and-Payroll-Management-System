import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewOrganizationDocs } from './view-organization-docs';

describe('ViewOrganizationDocs', () => {
  let component: ViewOrganizationDocs;
  let fixture: ComponentFixture<ViewOrganizationDocs>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViewOrganizationDocs]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewOrganizationDocs);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
