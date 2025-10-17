import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BusinessUnitDataDto } from '../../dto/BusinessUnitDataDto';
import { OrganizationBusinessUnitService } from '../../service/organization-business-unit-service';

@Component({
  selector: 'app-organization-business-units',
  standalone: false,
  templateUrl: './organization-business-units.html',
  styleUrl: './organization-business-units.css'
})
export class OrganizationBusinessUnits {
  displayedColumns: string[] = ['#', 'businessUnitName', 'actions'];
  businessUnitForm!: FormGroup;
  businessUnits: BusinessUnitDataDto[] = [];
  showForm = false;

  editingBusinessUnitId: Number | null = null;
  editForm!: FormGroup;
  errorMessage: string | null = null;

  constructor(private fb: FormBuilder, private businessUnitService: OrganizationBusinessUnitService) {
    this.businessUnitForm = this.fb.group({
      businessUnitName: ['', [Validators.required, Validators.minLength(2)]]
    });
    this.editForm = this.fb.group({
      editedBusinessUnitName: ['', [Validators.required, Validators.minLength(2)]]
    });
  }

  ngOnInit(){
    this.loadBusinessUnits();
  }

  loadBusinessUnits(){
    this.businessUnitService.getBusinessUnits().subscribe({
      next: (response: any) => {
        this.businessUnits = response.businessUnits;
      },
      error: (err) => {
        console.error('Error fetching business Units:', err);
        console.log(err.error)

        this.errorMessage="Error fetching business Units"
      }
    });
  }

  startEdit(businessUnit: BusinessUnitDataDto): void {
    this.editingBusinessUnitId = businessUnit.businessUnitId;
    this.editForm.get('editedBusinessUnitName')?.setValue(businessUnit.businessUnitName);
  }

  saveEdit(businessUnit: BusinessUnitDataDto): void {
    const updatedName = this.editForm.value['editedBusinessUnitName']
    if (updatedName && updatedName.trim()) {
      businessUnit.businessUnitName = updatedName.trim();

      this.businessUnitService.updateBusinessUnit(businessUnit).subscribe({
        next: () => {
          this.editForm.get('editedBusinessUnitName')?.setValue(null);
          this.editingBusinessUnitId = null;
        },
        error: (err) => {
          console.error('Error updating business unit:', err);
          this.errorMessage=err.error;
        }
      });
    }
  }

  cancelEdit(): void {
      this.editingBusinessUnitId = null;
  }

  toggleForm(): void {
    this.showForm = !this.showForm;
  }

  addBusinessUnit(): void {
    if (this.businessUnitForm.valid) {
      const businessUnitName = this.businessUnitForm.value.businessUnitName.trim();

      this.businessUnitService.addBusinessUnit(businessUnitName).subscribe({
        next: () => {
          this.toggleForm();
          this.loadBusinessUnits();
        },
        error: (err) => {
          console.error('Error adding business unit:', err);
          this.errorMessage=err.error?.error;
        }
      });
    }
  }

  deleteBusinessUnit(businessUnitId: Number): void {
    if (confirm('Are you sure you want to delete this business unit?')) {
      this.businessUnitService.deleteBusinessUnit(businessUnitId).subscribe({
        next: () => {
          this.loadBusinessUnits();
        },
        error: (err) => {
          console.error('Error deleting business unit:', err);
          this.errorMessage=err.error;
        }
      });
    }
  }

  clearError(): void {
    this.errorMessage = null;
  }
}
