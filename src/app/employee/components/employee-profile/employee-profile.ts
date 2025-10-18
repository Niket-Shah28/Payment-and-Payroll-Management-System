import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EmployeeProfileService } from '../../services/employee-profile-service';
import { EmployeeContactDetailsResponseDto } from '../../dto/employee-contact-details-response-dto';
import { ProfileResponseDto } from '../../dto/profile-response-dto';
import { EmployeeAddressResponseDto } from '../../dto/employee-address-response-dto';
import { EmployeeDesignationDto } from '../../dto/EmployeeDesignation-dto';

@Component({
  selector: 'app-employee-profile',
  standalone: false,
  templateUrl: './employee-profile.html',
  styleUrls: ['./employee-profile.css'],
})
export class EmployeeProfile implements OnInit {
  profileForm!: FormGroup;
  addressForm!: FormGroup;
  contactForm!: FormGroup;

  profileData!: ProfileResponseDto;
  addressData!: EmployeeAddressResponseDto;
  contactData!: EmployeeContactDetailsResponseDto;
  designationData!: EmployeeDesignationDto;

  editingProfile = false;
  editingAddress = false;
  editingContact = false;
  selectedFile?: File;

  constructor(private fb: FormBuilder, private profileService: EmployeeProfileService) {}

  ngOnInit(): void {
    // Initialize forms
    this.profileForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.maxLength(50)]],
      middleName: ['', [Validators.maxLength(50)]],
      lastName: ['', [Validators.required, Validators.maxLength(50)]],
      gender: ['', Validators.required],
      salutation: ['', Validators.required],
      spouse: [''],
      dateOfBirth: ['', Validators.required],
      bloodGroup: [''],
      nationality: [''],
      panNumber: ['', Validators.required],
      aadharNumber: ['', Validators.required],
    });

    this.addressForm = this.fb.group({
      currentAddress: ['', Validators.required],
      permanentAddress: ['', Validators.required],
      city: ['', Validators.required],
      state: ['', Validators.required],
      pincode: ['', Validators.required],
      country: ['', Validators.required],
    });

    this.contactForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      officeEmail: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', Validators.required],
      emergencyContact: [''],
      emergencyContactRelation: [''],
      alternateMobileNumber: [''],
    });

    this.loadProfile();
    this.loadAddress();
    this.loadContact();
    this.loadDesignation();
  }

  private loadProfile() {
    this.profileService.getProfile().subscribe({
      next: (res) => {
        this.profileData = res;
        this.profileForm.patchValue(res);
      },
      error: (err) => console.error('Error loading profile:', err),
    });
  }

  private loadAddress() {
    this.profileService.getAddress().subscribe({
      next: (res) => {
        this.addressData = res;
        this.addressForm.patchValue(res);
      },
      error: (err) => console.error('Error loading address:', err),
    });
  }

  private loadContact() {
    this.profileService.getContactDetails().subscribe({
      next: (res) => {
        this.contactData = res;
        this.contactForm.patchValue(res);
      },
      error: (err) => console.error('Error loading contact:', err),
    });
  }

  private loadDesignation() {
    this.profileService.getDesignation().subscribe({
      next: (res) => (this.designationData = res),
      error: (err) => console.error('Error loading designation:', err),
    });
  }

  toggleEdit(section: string) {
    if (section === 'profile') this.editingProfile = !this.editingProfile;
    if (section === 'address') this.editingAddress = !this.editingAddress;
    if (section === 'contact') this.editingContact = !this.editingContact;

    // Reset form to original values when cancelling
    if (!this.editingProfile && section === 'profile') this.profileForm.patchValue(this.profileData);
    if (!this.editingAddress && section === 'address') this.addressForm.patchValue(this.addressData);
    if (!this.editingContact && section === 'contact') this.contactForm.patchValue(this.contactData);
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  submitProfile() {
    console.log('Submitting profile...', this.profileForm.value, 'Valid:', this.profileForm.valid);

    if (this.profileForm.valid) {
      const formData = new FormData();
      formData.append('profileData', new Blob([JSON.stringify(this.profileForm.value)], { type: 'application/json' }));
      if (this.selectedFile) formData.append('profilePhoto', this.selectedFile);

      this.profileService.updateProfile(formData).subscribe({
        next: (res) => {
          console.log('Profile updated successfully', res);
          this.profileData = res;
          this.editingProfile = false;
        },
        error: (err) => console.error('Profile update failed:', err),
      });
    } else {
      alert('Please fill all required fields before submitting.');
    }
  }

  submitAddress() {
    if (this.addressForm.valid) {
      this.profileService.updateAddress(this.addressForm.value).subscribe({
        next: (res) => {
          console.log('Address updated successfully', res);
          this.addressData = res;
          this.editingAddress = false;
        },
        error: (err) => console.error('Address update failed:', err),
      });
    } else {
      alert('Please fill all required fields before submitting.');
    }
  }

  submitContact() {
    if (this.contactForm.valid) {
      this.profileService.updateContactDetails(this.contactForm.value).subscribe({
        next: (res) => {
          console.log('Contact updated successfully', res);
          this.contactData = res;
          this.editingContact = false;
        },
        error: (err) => console.error('Contact update failed:', err),
      });
    } else {
      alert('Please fill all required fields before submitting.');
    }
  }

  cancelEdit(section: string) {
    this.toggleEdit(section);
  }
}
