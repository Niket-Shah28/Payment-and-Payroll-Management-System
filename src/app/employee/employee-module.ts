import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EmployeeRoutingModule } from './employee-routing-module';
import { EmployeeDashboard } from './components/employee-dashboard/employee-dashboard';


@NgModule({
  declarations: [
    EmployeeDashboard
  ],
  imports: [
    CommonModule,
    EmployeeRoutingModule
  ]
})
export class EmployeeModule { }
