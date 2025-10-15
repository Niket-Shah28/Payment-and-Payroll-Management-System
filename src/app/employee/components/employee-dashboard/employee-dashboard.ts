import { Component } from '@angular/core';

@Component({
  selector: 'app-employee-dashboard',
  standalone: false,
  templateUrl: './employee-dashboard.html',
  styleUrl: './employee-dashboard.css'
})
export class EmployeeDashboard {

   isNavbarOpen = false;

  tabs = [
    { name: 'HOME', link: '/home' },
    { name: 'RAISE TICKETS', link: '/raise-tickets' },
    { name: 'ACCOUNT DETAILS', link: '/account-details' },
    { name: 'PAYSLIP', link: '/payslip' },
    { name: 'MARK ATTENDANCE', link: '/mark-attendance' },
    { name: 'APPLY LEAVE', link: '/apply-leave' },
   
  ];

  toggleNavbar() {
    this.isNavbarOpen = !this.isNavbarOpen;
  }

  closeNavbar() {
    this.isNavbarOpen = false;
  }

}
