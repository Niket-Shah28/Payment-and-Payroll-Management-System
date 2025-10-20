import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganizationTickets } from './organization-tickets';

describe('OrganizationTickets', () => {
  let component: OrganizationTickets;
  let fixture: ComponentFixture<OrganizationTickets>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OrganizationTickets]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrganizationTickets);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
