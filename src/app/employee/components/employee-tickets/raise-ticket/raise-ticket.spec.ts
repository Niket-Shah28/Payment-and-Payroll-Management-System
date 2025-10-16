import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RaiseTicket } from './raise-ticket';

describe('RaiseTicket', () => {
  let component: RaiseTicket;
  let fixture: ComponentFixture<RaiseTicket>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RaiseTicket]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RaiseTicket);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
