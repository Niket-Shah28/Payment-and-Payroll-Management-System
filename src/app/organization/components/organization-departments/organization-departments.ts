import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { OrganizationDepartmentService } from '../../service/organization-department-service';
import { DepartmentDataDto } from '../../dto/DepartmentDataDto';

@Component({
  selector: 'app-organization-departments',
  standalone: false,
  templateUrl: './organization-departments.html',
  styleUrl: './organization-departments.css'
})
export class OrganizationDepartments implements OnInit{

  displayedColumns: string[] = ['#', 'departmentName', 'actions'];
  departmentForm!: FormGroup;
  departments: DepartmentDataDto[] = [];
  showForm = false;

  editingDepartmentId: Number | null = null;
  editForm!: FormGroup;
  errorMessage: string | null = null;

  constructor(private fb: FormBuilder, private departmentService: OrganizationDepartmentService) {
    this.departmentForm = this.fb.group({
      deptName: ['', [Validators.required, Validators.minLength(2)]]
    });
    this.editForm = this.fb.group({
      editedDeptName: ['', [Validators.required, Validators.minLength(2)]]
    });
  }

  ngOnInit(){
    this.loadDepartments();
  }

  loadDepartments(){
    this.departmentService.getDepartments().subscribe({
      next: (response: any) => {
        this.departments = response.departments;
      },
      error: (err) => {
        console.error('Error fetching departments:', err);
        console.log(err.error)

        this.errorMessage="Error fetching departments"
      }
    });
  }

  startEdit(dept: DepartmentDataDto): void {
    this.editingDepartmentId = dept.departmentId;
    this.editForm.get('editedDeptName')?.setValue(dept.departmentName);
  }

  saveEdit(dept: DepartmentDataDto): void {
    const updatedName = this.editForm.value['editedDeptName']
    if (updatedName && updatedName.trim()) {
      dept.departmentName = updatedName.trim();

      this.departmentService.updateDepartment(dept).subscribe({
        next: () => {
          this.editForm.get('editedDeptName')?.setValue(null);
          this.editingDepartmentId = null;
        },
        error: (err) => {
          console.error('Error updating department:', err);
          this.errorMessage=err.error;
        }
      });
    }
  }

  cancelEdit(): void {
      this.editingDepartmentId = null;
  }

  toggleForm(): void {
    this.showForm = !this.showForm;
  }

  addDepartment(): void {
    if (this.departmentForm.valid) {
      const deptName = this.departmentForm.value.deptName.trim();

      this.departmentService.addDepartment(deptName).subscribe({
        next: () => {
          this.toggleForm();
          this.loadDepartments();
        },
        error: (err) => {
          console.error('Error adding department:', err);
          this.errorMessage=err.error?.error;
        }
      });
    }
  }

  deleteDepartment(deptId: Number): void {
    if (confirm('Are you sure you want to delete this department?')) {
      this.departmentService.deleteDepartment(deptId).subscribe({
        next: () => {
          this.loadDepartments();
        },
        error: (err) => {
          console.error('Error deleting department:', err);
          this.errorMessage=err.error;
        }
      });
    }
  }

  clearError(): void {
    this.errorMessage = null;
  }
}
