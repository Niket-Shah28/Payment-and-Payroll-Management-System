import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OrganizationRoutingModule } from './organization-routing-module';
import { OrganizationDepartments } from './components/organization-departments/organization-departments';
import { OrganizationLayoutComponent } from './components/organization-layout-component/organization-layout-component';
import { HomePage } from './components/home-page/home-page';

import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { OrganizationBusinessUnits } from './components/organization-business-units/organization-business-units';
import { OrganizationEmployeeDesignations } from './components/organization-employee-designations/organization-employee-designations';
import { BankAccount } from './components/bank-account/bank-account';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { OrganizationVendors } from './components/organization-vendors/organization-vendors';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { OrganizationPayoutComponent } from './components/organization-payout-component/organization-payout-component';
import { MatDividerModule } from '@angular/material/divider';
import { MatRadioButton, MatRadioModule } from '@angular/material/radio';


@NgModule({
  declarations: [
    OrganizationLayoutComponent,
    OrganizationDepartments,
    OrganizationLayoutComponent,
    HomePage,
    OrganizationBusinessUnits,
    OrganizationEmployeeDesignations,
    BankAccount,
    OrganizationVendors,
    OrganizationPayoutComponent
  ],
  imports: [
    CommonModule,
    OrganizationRoutingModule,
    MatCardModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatToolbarModule,
    MatFormFieldModule,
    MatInputModule,
    MatOptionModule,
    ReactiveFormsModule,
    MatSelectModule,
    MatProgressSpinnerModule,
    MatDividerModule,
    MatRadioModule
  ]
})
export class OrganizationModule { }
