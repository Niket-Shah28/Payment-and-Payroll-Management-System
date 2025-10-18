import { Component, OnInit } from '@angular/core';
import { EmployeeService } from '../../services/employee-service';
import { ProfileResponseDto } from '../../dto/profile-response-dto';

@Component({
  selector: 'app-employee-dashboard',
  standalone: false,
  templateUrl: './employee-dashboard.html',
  styleUrl: './employee-dashboard.css'
})
export class EmployeeDashboard implements OnInit {

  isNavbarOpen = false;
  profilePhotoUrl: string = 'assets/default-avatar.png'; // fallback image

  tabs = [
    { name: 'HOME', link: 'home' },
    { name: 'PROFILE', link: 'profile' },
    { name: 'RAISE TICKETS', link: 'raise-tickets' },
    { name: 'ACCOUNT DETAILS', link: 'bank-account-list' },
    { name: 'PAYSLIP', link: 'payslip' },
    { name: 'MARK ATTENDANCE', link: 'mark-attendance' },
    { name: 'APPLY LEAVE', link: 'apply-leave' },
    {name: 'DOCUMENTS', link:'documents'}
  ];

  constructor(private employeeService: EmployeeService) { }

  ngOnInit(): void {
    this.fetchProfilePhoto();
  }

  fetchProfilePhoto(): void {
    this.employeeService.getProfile().subscribe({
      next: (profile: ProfileResponseDto) => {
        if (profile.profilePhotoUrl) {
          this.profilePhotoUrl = profile.profilePhotoUrl;
        }
      },
      error: (err) => {
        console.error('Error fetching profile photo:', err);
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
