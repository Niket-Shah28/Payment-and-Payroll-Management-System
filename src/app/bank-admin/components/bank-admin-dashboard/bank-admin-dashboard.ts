import { Component } from '@angular/core';
import { BankAdminService } from '../../services/bank-admin-service';

@Component({
  selector: 'app-bank-admin-dashboard',
  standalone: false,
  templateUrl: './bank-admin-dashboard.html',
  styleUrl: './bank-admin-dashboard.css'
})
export class BankAdminDashboard {

  isNavbarOpen = false;
    profilePhotoUrl: string = 'assets/default-avatar.png'; // fallback image
  
    tabs = [
      { name: 'HOME', link: 'home' },
     // { name: 'MANAGE ORGANIZATIONS', link: 'manage-organizations' },
      { name: 'APPROVE ORGANIZATIONS', link: 'approve-organizations' },
      { name: 'VIEW ORGANIZATION DOCS', link: 'view-organization-docs' },
      { name: 'APPROVE PAYMENT REQUESTS', link: 'approve-payment-requests' },
      //{ name: 'REGISTER ORGANIZATION', link: 'register-organization' }
    ];
  
    constructor(private bankAdminService: BankAdminService) { }

  ngOnInit(): void {
      //this.fetchProfilePhoto();
    }
  
    // fetchProfilePhoto(): void {
    //   this.employeeService.getProfile().subscribe({
    //     next: (profile: ProfileResponseDto) => {
    //       if (profile.profilePhotoUrl) {
    //         this.profilePhotoUrl = profile.profilePhotoUrl;
    //       }
    //     },
    //     error: (err) => {
    //       console.error('Error fetching profile photo:', err);
    //     }
    //   });
    // }
  
  
    toggleNavbar() {
      this.isNavbarOpen = !this.isNavbarOpen;
    }
  
    closeNavbar() {
      this.isNavbarOpen = false;
    }

}
