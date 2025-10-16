import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EmployeeProfileService } from '../../services/employee-profile-service';
import { EmployeeContactDetailsResponseDto } from '../../dto/employee-contact-details-response-dto';
import { ProfileResponseDto } from '../../dto/profile-response-dto';
import { EmployeeAddressResponseDto } from '../../dto/employee-address-response-dto';



@Component({
  selector: 'app-employee-profile',
  standalone: false,
  templateUrl: './employee-profile.html',
  styleUrls: ['./employee-profile.css']
})
export class EmployeeProfile implements OnInit {

 profileForm!: FormGroup;
  addressForm!: FormGroup;
  contactForm!: FormGroup;

  profileData!: ProfileResponseDto;
  addressData!: EmployeeAddressResponseDto;
  contactData!: EmployeeContactDetailsResponseDto;

  editingProfile = false;
  editingAddress = false;
  editingContact = false;
  selectedFile?: File;

  constructor(private fb: FormBuilder, private profileService: EmployeeProfileService) { }

  ngOnInit(): void {
    this.loadProfile();
    this.loadAddress();
    this.loadContact();
  }

  private loadProfile() {
    this.profileService.getProfile().subscribe(res => {
      this.profileData = res;
      this.profileForm = this.fb.group({
        firstName: [res.firstName, [Validators.required, Validators.maxLength(50)]],
        middleName: [res.middleName, [Validators.maxLength(50)]],
        lastName: [res.lastName, [Validators.required, Validators.maxLength(50)]],
        gender: [res.gender, Validators.required],
        salutation: [res.salutation, Validators.required],
        spouse: [res.spouse],
        dateOfBirth: [res.dateOfBirth, Validators.required],
        bloodGroup: [res.bloodGroup],
        nationality: [res.nationality],
        panNumber: [res.panNumber, Validators.required],
        aadharNumber: [res.aadharNumber, Validators.required]
      });
    });
  }

  private loadAddress() {
    this.profileService.getAddress().subscribe(res => {
      this.addressData = res;
      this.addressForm = this.fb.group({
        currentAddress: [res.currentAddress, Validators.required],
        permanentAddress: [res.permanentAddress, Validators.required],
        city: [res.city, Validators.required],
        state: [res.state, Validators.required],
        pincode: [res.pincode, Validators.required],
        country: [res.country, Validators.required]
      });
    });
  }

  private loadContact() {
    this.profileService.getContactDetails().subscribe(res => {
      this.contactData = res;
      this.contactForm = this.fb.group({
        email: [res.email, [Validators.required, Validators.email]],
        officeEmail: [res.officeEmail, [Validators.required, Validators.email]],
        phoneNumber: [res.phoneNumber, Validators.required],
        emergencyContact: [res.emergencyContact],
        emergencyContactRelation: [res.emergencyContactRelation],
        alternateMobileNumber: [res.alternateMobileNumber]
      });
    });
  }

  toggleEdit(section: string) {
    if (section === 'profile') this.editingProfile = !this.editingProfile;
    if (section === 'address') this.editingAddress = !this.editingAddress;
    if (section === 'contact') this.editingContact = !this.editingContact;
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  submitProfile() {
    if (this.profileForm.valid) {
      this.profileService.updateProfile(this.profileForm.value, this.selectedFile).subscribe(res => {
        this.profileData = res;
        this.editingProfile = false;
      });
    }
  }

  submitAddress() {
    if (this.addressForm.valid) {
      this.profileService.updateAddress(this.addressForm.value).subscribe(res => {
        this.addressData = res;
        this.editingAddress = false;
      });
    }
  }

  submitContact() {
    if (this.contactForm.valid) {
      this.profileService.updateContactDetails(this.contactForm.value).subscribe(res => {
        this.contactData = res;
        this.editingContact = false;
      });
    }
  }

  cancelEdit(section: string) {
    if (section === 'profile') this.loadProfile(); 
    if (section === 'address') this.loadAddress(); 
    if (section === 'contact') this.loadContact(); 

    this.toggleEdit(section);
  }

}
