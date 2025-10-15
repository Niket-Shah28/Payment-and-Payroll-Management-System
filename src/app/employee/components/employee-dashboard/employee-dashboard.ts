import { Component } from '@angular/core';
import { EmployeeService } from '../../services/employee-service';

@Component({
  selector: 'app-employee-dashboard',
  standalone: false,
  templateUrl: './employee-dashboard.html',
  styleUrl: './employee-dashboard.css'
})
export class EmployeeDashboard {

   isNavbarOpen = false;
  // Initialize username to a loading state
  username: string = 'Fetching ID...'; 

  tabs = [
    // Use relative links
    { name: 'HOME', link: 'home' }, 
    { name: 'RAISE TICKETS', link: 'raise-tickets' },
    { name: 'ACCOUNT DETAILS', link: 'account-details' },
    { name: 'PAYSLIP', link: 'payslip' },
    { name: 'MARK ATTENDANCE', link: 'mark-attendance' },
    { name: 'APPLY LEAVE', link: 'apply-leave' },
  ];

  constructor(private employeeService: EmployeeService) { }

  ngOnInit(): void {
    this.fetchEmployeeReferenceId();
  }

  fetchEmployeeReferenceId(): void {
    console.log('Attempting to fetch employee Reference ID...');
    
    this.employeeService.getEmployeeReferenceId().subscribe({
      next: (employeeData:any) => {
        // SUCCESS: Check the data structure before assigning
        if (employeeData && employeeData.referenceId) {
            this.username = employeeData.referenceId;
            console.log('Successfully fetched Reference ID:', this.username);
        } else {
            this.username = 'ID Missing (API Success)';
            console.error('API call succeeded, but "referenceId" field was missing in the response body.', employeeData);
        }
      },
      error: (error:any) => {
        // ERROR: Log detailed error object for network issues (CORS, 401, 404, 500)
        this.username = 'Error Fetching ID'; 
        console.error('CRITICAL API ERROR: Could not fetch Reference ID.', error);
        console.error('Check Network Tab: Status code:', error.status, 'Message:', error.message);
      }
    });
  }

  toggleNavbar() {
    this.isNavbarOpen = !this.isNavbarOpen;
  }

  closeNavbar() {
    this.isNavbarOpen = false;
  }

}
