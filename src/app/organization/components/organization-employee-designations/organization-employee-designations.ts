import { Component } from '@angular/core';
import { EmployeeRoleDto } from '../../dto/EmployeeRoleDto';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { OrganizationEmployeeDesignationService } from '../../service/organization-employee-designation-service';

@Component({
  selector: 'app-organization-employee-designations',
  standalone: false,
  templateUrl: './organization-employee-designations.html',
  styleUrl: './organization-employee-designations.css'
})
export class OrganizationEmployeeDesignations {
  displayedColumns: string[] = ['#', 'role', 'actions'];
  employeeRoleForm!: FormGroup;
  roles: EmployeeRoleDto[] = [];
  showForm = false;

  editingRoleId: Number | null = null;
  editForm!: FormGroup;
  errorMessage: string | null = null;

  constructor(private fb: FormBuilder, private designationService: OrganizationEmployeeDesignationService) {
    this.employeeRoleForm = this.fb.group({
      role: ['', [Validators.required, Validators.minLength(2)]]
    });
    this.editForm = this.fb.group({
      editedRoleName: ['', [Validators.required, Validators.minLength(2)]]
    });
  }

  ngOnInit(){
    this.loadEmployeeRoles();
  }

  loadEmployeeRoles(){
    this.designationService.getRoles().subscribe({
      next: (response: any) => {
        this.roles = response.roles;
      },
      error: (err) => {
        console.error('Error fetching roles:', err);
        console.log(err.error)

        this.errorMessage="Error fetching roles"
      }
    });
  }

  startEdit(role: any): void {
    this.editingRoleId = role.roleId;
    this.editForm.get('editedRoleName')?.setValue(role.role);
  }

  saveEdit(role: any): void {
    const updatedName = this.editForm.value['editedRoleName']
    if (updatedName && updatedName.trim()) {
      role.role = updatedName.trim();

      this.designationService.updateRole(role).subscribe({
        next: () => {
          this.editForm.get('editedRoleName')?.setValue(null);
          this.editingRoleId = null;
        },
        error: (err) => {
          console.error('Error updating role:', err);
          this.errorMessage=err.error;
        }
      });
    }
  }

  cancelEdit(): void {
    this.editingRoleId = null;
  }

  toggleForm(): void {
    this.showForm = !this.showForm;
  }

  addRole(): void {
    if (this.employeeRoleForm.valid) {
      const employeeRoleName = this.employeeRoleForm.value.role.trim();

      this.designationService.addRole(employeeRoleName).subscribe({
        next: () => {
          this.toggleForm();
          this.loadEmployeeRoles();
        },
        error: (err) => {
          console.error('Error adding role:', err);
          this.errorMessage=err.error?.error;
        }
      });
    }
  }

  deleteRole(roleId: Number): void {
    if (confirm('Are you sure you want to delete this department?')) {
      this.designationService.deleteRole(roleId).subscribe({
        next: () => {
          this.loadEmployeeRoles();
        },
        error: (err) => {
          console.error('Error deleting role:', err);
          this.errorMessage=err.error;
        }
      });
    }
  }

  clearError(): void {
    this.errorMessage = null;
  }
}
