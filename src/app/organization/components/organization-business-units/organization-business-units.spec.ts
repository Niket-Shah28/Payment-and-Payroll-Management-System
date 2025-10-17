import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganizationBusinessUnits } from './organization-business-units';

describe('OrganizationBusinessUnits', () => {
  let component: OrganizationBusinessUnits;
  let fixture: ComponentFixture<OrganizationBusinessUnits>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OrganizationBusinessUnits]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrganizationBusinessUnits);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
