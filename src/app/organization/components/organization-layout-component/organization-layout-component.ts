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
    { name: 'HOME', link: '/home' },
    { name: 'MANAGE EMPLOYEES', link: '/manage-employees' },
    { name: 'MANAGE VENDORS', link: '/manage-vendors' },
    { name: 'MANAGE DEPARTMENT', link: '/organization/dashboard/departments' },
    { name: 'MANAGE BUSINESS UNITS', link: '/manage-business-units' },
    { name: 'MANAGE EMPLOYEE ROLES', link: '/manage-employee-roles' },
    { name: 'TICKETS', link: '/tickets' },
    { name: 'MANAGE BANK ACCOUNT', link: '/manage-bank-account' },
    { name: 'INITIATE PAYOUT', link: '/initiate-payout' }
  ];

  toggleNavbar() {
    this.isNavbarOpen = !this.isNavbarOpen;
  }

  closeNavbar() {
    this.isNavbarOpen = false;
  }
}
