import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OrganizationRoutingModule } from './organization-routing-module';
import { OrganizationDashboard } from './components/organization-dashboard/organization-dashboard';


@NgModule({
  declarations: [
    OrganizationDashboard
  ],
  imports: [
    CommonModule,
    OrganizationRoutingModule
  ]
})
export class OrganizationModule { }
