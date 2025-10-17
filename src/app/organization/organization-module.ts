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


@NgModule({
  declarations: [
    OrganizationLayoutComponent,
    OrganizationDepartments,
    OrganizationLayoutComponent,
    HomePage,
    OrganizationBusinessUnits,
    OrganizationEmployeeDesignations
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
    ReactiveFormsModule
  ]
})
export class OrganizationModule { }
