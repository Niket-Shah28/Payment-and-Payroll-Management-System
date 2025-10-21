import { Component } from '@angular/core';

@Component({
  selector: 'app-organization-layout-component',
  standalone: false,
  templateUrl: './organization-layout-component.html',
  styleUrl: './organization-layout-component.css'
})
export class OrganizationLayoutComponent {
  isNavbarOpen = false;

  tabs = [
    { name: 'HOME', link: '/organization/dashboard/home' },
    { name: 'MANAGE EMPLOYEES', link: '/organization/dashboard/employees' },
    { name: 'MANAGE VENDORS', link: '/organization/dashboard/vendors' },
    { name: 'MANAGE DEPARTMENT', link: '/organization/dashboard/departments' },
    { name: 'MANAGE BUSINESS UNITS', link: '/organization/dashboard/business-units' },
    { name: 'MANAGE EMPLOYEE ROLES', link: '/organization/dashboard/roles' },
    { name: 'TICKETS', link: '/organization/dashboard/organization-tickets' },
    { name: 'MANAGE BANK ACCOUNT', link: '/organization/dashboard/bank-account' },
    { name: 'INITIATE PAYOUT', link: '/organization/dashboard/initiate-payout' }
  ];

  toggleNavbar() {
    this.isNavbarOpen = !this.isNavbarOpen;
  }

  closeNavbar() {
    this.isNavbarOpen = false;
  }
  

}
