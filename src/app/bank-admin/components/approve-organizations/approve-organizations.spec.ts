import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApproveOrganizations } from './approve-organizations';

describe('ApproveOrganizations', () => {
  let component: ApproveOrganizations;
  let fixture: ComponentFixture<ApproveOrganizations>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ApproveOrganizations]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ApproveOrganizations);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
