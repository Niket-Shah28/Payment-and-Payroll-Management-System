import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { OrganizationRoutingModule } from './organization-routing-module';
import { OrganizationDepartments } from './components/organization-departments/organization-departments';
import { OrganizationLayoutComponent } from './components/organization-layout-component/organization-layout-component';
import { HomePage } from './components/home-page/home-page';
import { OrganizationBusinessUnits } from './components/organization-business-units/organization-business-units';
import { OrganizationEmployeeDesignations } from './components/organization-employee-designations/organization-employee-designations';
import { BankAccount } from './components/bank-account/bank-account';
import { OrganizationVendors } from './components/organization-vendors/organization-vendors';
import { OrganizationPayoutComponent } from './components/organization-payout-component/organization-payout-component';

// Angular Material Imports
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDividerModule } from '@angular/material/divider';
import { MatRadioModule } from '@angular/material/radio';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { OrganizationTickets } from './components/organization-tickets/organization-tickets';
import { OrganizationEmployees } from './components/organization-employees/organization-employees';

import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatSortModule } from '@angular/material/sort';
import { MatChipsModule } from '@angular/material/chips';
import { AddEmployeeDialogComponent } from './components/add-employee-dialog-component/add-employee-dialog-component';
import { MatDialogModule } from '@angular/material/dialog';
import { MatAutocompleteModule } from '@angular/material/autocomplete';

@NgModule({
  declarations: [
    OrganizationLayoutComponent,
    OrganizationDepartments,
    HomePage,
    OrganizationBusinessUnits,
    OrganizationEmployeeDesignations,
    BankAccount,
    OrganizationVendors,
    OrganizationPayoutComponent,
    OrganizationTickets,
    OrganizationEmployees,
    AddEmployeeDialogComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    OrganizationRoutingModule,
    
    // Material Modules
    MatCardModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatToolbarModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatOptionModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatProgressSpinnerModule,
    MatDividerModule,
    MatRadioModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatPaginatorModule,
    MatTooltipModule,
    MatSortModule,
    MatChipsModule,
    MatDialogModule,
    MatAutocompleteModule
  ]
})
export class OrganizationModule { }