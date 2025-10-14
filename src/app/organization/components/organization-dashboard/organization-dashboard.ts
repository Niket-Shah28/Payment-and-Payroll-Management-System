import { Component } from '@angular/core';

@Component({
  selector: 'app-organization-dashboard',
  standalone: false,
  templateUrl: './organization-dashboard.html',
  styleUrl: './organization-dashboard.css'
})
export class OrganizationDashboard {
  isNavbarOpen = false;

  tabs = [
    { name: 'HOME', link: '/home' },
    { name: 'MANAGE EMPLOYEES', link: '/manage-employees' },
    { name: 'MANAGE VENDORS', link: '/manage-vendors' },
    { name: 'MANAGE DEPARTMENT', link: '/manage-department' },
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
