import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BankAdminRoutingModule } from './bank-admin-routing-module';
import { BankAdminDashboard } from './components/bank-admin-dashboard/bank-admin-dashboard';


@NgModule({
  declarations: [
    BankAdminDashboard
  ],
  imports: [
    CommonModule,
    BankAdminRoutingModule
  ]
})
export class BankAdminModule { }
