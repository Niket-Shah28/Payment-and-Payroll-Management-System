import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OrganizationRoutingModule } from './organization-routing-module';
import { OrganizationDepartments } from './components/organization-departments/organization-departments';
import { OrganizationLayoutComponent } from './components/organization-layout-component/organization-layout-component';
import { HomePage } from './components/home-page/home-page';


@NgModule({
  declarations: [
    OrganizationLayoutComponent,
    OrganizationDepartments,
    OrganizationLayoutComponent,
    HomePage
  ],
  imports: [
    CommonModule,
    OrganizationRoutingModule
  ]
})
export class OrganizationModule { }
