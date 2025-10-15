import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EmployeeRoutingModule } from './employee-routing-module';
import { EmployeeDashboard } from './components/employee-dashboard/employee-dashboard';
import { HttpClientModule } from '@angular/common/http';
import { HomeComponent } from './components/employee-home/home-component/home-component';


@NgModule({
  declarations: [
    EmployeeDashboard,
    HomeComponent
  ],
  imports: [
    CommonModule,
    EmployeeRoutingModule,
    HttpClientModule
  ]
})
export class EmployeeModule { }
