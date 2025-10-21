import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { EmployeeRole } from '../../dto/EmployeeRole';
import { Department } from '../../dto/Department';
import { BusinessUnit } from '../../dto/BusinessUnit';
import { Manager } from '../../dto/Manager';
import { OrganizationCoreDataService } from '../../service/organization-core-data-service';

@Component({
  selector: 'app-add-employee-dialog',
  standalone: false,
  templateUrl: './add-employee-dialog-component.html',
  styleUrls: ['./add-employee-dialog-component.css']
})
export class AddEmployeeDialogComponent implements OnInit {
  employeeForm: FormGroup;
  isSubmitting = false;
  
  // Dropdown data
  roles: EmployeeRole[] = [];
  departments: Department[] = [];
  businessUnits: BusinessUnit[] = [];
  managers: Manager[] = [];
  filteredManagers: Manager[] = [];
  
  // Enums
  genders = ['MALE', 'FEMALE', 'OTHER'];
  salutations = ['Mr', 'Mrs', 'Miss', 'Ms'];
  bloodGroups = ['A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-'];
  
  maxDate = new Date(); // For date of birth validation

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<AddEmployeeDialogComponent>,
    private organizationCoreService: OrganizationCoreDataService
    // Inject your service here to fetch roles, departments, business units, and managers
    // private employeeService: EmployeeService
  ) {
    this.employeeForm = this.createForm();
  }

  ngOnInit(): void {
    this.loadDropdownData();
    this.setupSalaryCalculation();
  }

  private createForm(): FormGroup {
    return this.fb.group({
      // Personal Information
      firstName: ['', [Validators.required, Validators.minLength(2)]],
      middleName: [''],
      lastName: ['', [Validators.required, Validators.minLength(2)]],
      gender: ['', Validators.required],
      salutation: ['', Validators.required],
      spouse: [''],
      dateOfBirth: ['', Validators.required],
      bloodGroup: ['', Validators.required],
      nationality: ['', Validators.required],
      
      // Government IDs
      panNumber: ['', [Validators.required, Validators.pattern(/^[A-Z]{5}[0-9]{4}[A-Z]{1}$/)]],
      aadharNumber: ['', [Validators.required, Validators.pattern(/^[0-9]{12}$/)]],
      
      // Organizational
      managerId: [''],
      
      // Contact Information
      officeEmail: ['', [Validators.required, Validators.email]],
      personalEmail: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
      
      // Salary Components
      basicSalary: [0, [Validators.required, Validators.min(0)]],
      houseRentAllowance: [0, [Validators.required, Validators.min(0)]],
      dearnessAllowance: [0, [Validators.required, Validators.min(0)]],
      providentFund: [0, [Validators.required, Validators.min(0)]],
      otherAllowance: [0, [Validators.required, Validators.min(0)]],
      finalSalary: [{ value: 0, disabled: true }],
      
      // Position Details
      grade: ['', [Validators.required, Validators.min(1)]],
      employeeRoleId: ['', Validators.required],
      departmentId: ['', Validators.required],
      businessUnitId: ['', Validators.required]
    });
  }

  private loadDropdownData(): void {
    // TODO: Replace with actual API calls
    // Example:
    // this.employeeService.getRoles().subscribe(data => this.roles = data);
    // this.employeeService.getDepartments().subscribe(data => this.departments = data);
    // this.employeeService.getBusinessUnits().subscribe(data => this.businessUnits = data);
    // this.employeeService.getManagers().subscribe(data => {
    //   this.managers = data;
    //   this.filteredManagers = data;
    // });


    this.organizationCoreService.getCoreData().subscribe({
      next: (data) => {
        this.roles = data.roles;
        this.departments = data.departments;
        this.businessUnits = data.businessUnits;
        this.managers = data.managers;
        this.filteredManagers = data.managers;
      },
      error: (error) => {
        console.error('Error loading core data:', error);
      }
    });
    
    this.managers = [
      { employeeId: 'EMP-001', name: 'John Doe' },
      { employeeId: 'EMP-002', name: 'Jane Smith' },
      { employeeId: 'EMP-003', name: 'Robert Johnson' }
    ];
    
    this.filteredManagers = this.managers;
  }

  private setupSalaryCalculation(): void {
    // Watch salary fields and calculate final salary
    const salaryFields = ['basicSalary', 'houseRentAllowance', 'dearnessAllowance', 'providentFund', 'otherAllowance'];
    
    salaryFields.forEach(field => {
      this.employeeForm.get(field)?.valueChanges.subscribe(() => {
        this.calculateFinalSalary();
      });
    });
  }

  calculateFinalSalary(): void {
    const basic = this.employeeForm.get('basicSalary')?.value || 0;
    const hra = this.employeeForm.get('houseRentAllowance')?.value || 0;
    const da = this.employeeForm.get('dearnessAllowance')?.value || 0;
    const pf = this.employeeForm.get('providentFund')?.value || 0;
    const other = this.employeeForm.get('otherAllowance')?.value || 0;
    
    const finalSalary = basic + hra + da + pf + other;
    this.employeeForm.get('finalSalary')?.setValue(finalSalary, { emitEvent: false });
  }

  onManagerSearch(event: any): void {
    const searchValue = event.target.value.toLowerCase();
    this.filteredManagers = this.managers.filter(manager =>
      manager.name.toLowerCase().includes(searchValue) ||
      manager.employeeId.toLowerCase().includes(searchValue)
    );
  }

  displayManagerFn(managerId: string): string {
    if (!managerId) return '';
    const manager = this.managers.find(m => m.employeeId === managerId);
    return manager ? `${manager.name} (${manager.employeeId})` : managerId;
  }

  onSubmit(): void {
    if (this.employeeForm.invalid) {
      this.employeeForm.markAllAsTouched();
      return;
    }

    this.isSubmitting = true;
    
    // Get form values and include calculated finalSalary
    const formValue = {
      ...this.employeeForm.getRawValue(),
      finalSalary: this.employeeForm.get('finalSalary')?.value
    };
    console.log(formValue)

    // TODO: Call your API service to create employee
    // this.employeeService.addEmployee(formValue).subscribe({
    //   next: (response) => {
    //     this.dialogRef.close(response);
    //   },
    //   error: (error) => {
    //     console.error('Error adding employee:', error);
    //     this.isSubmitting = false;
    //   }
    // });

    // Mock API call
    setTimeout(() => {
      console.log('Employee data to submit:', formValue);
      this.dialogRef.close(formValue);
    }, 1000);
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  // Helper method to get error messages
  getErrorMessage(fieldName: string): string {
    const control = this.employeeForm.get(fieldName);
    if (!control) return '';

    if (control.hasError('required')) {
      return 'This field is required';
    }
    if (control.hasError('email')) {
      return 'Please enter a valid email';
    }
    if (control.hasError('pattern')) {
      if (fieldName === 'panNumber') return 'Invalid PAN format (e.g., ABCDE1234F)';
      if (fieldName === 'aadharNumber') return 'Invalid Aadhar (12 digits)';
      if (fieldName === 'phoneNumber') return 'Invalid phone number (10 digits)';
    }
    if (control.hasError('minLength')) {
      return `Minimum length is ${control.errors?.['minLength'].requiredLength}`;
    }
    if (control.hasError('min')) {
      return `Minimum value is ${control.errors?.['min'].min}`;
    }

    return '';
  }
}
