import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BankAdminRoutingModule } from './bank-admin-routing-module';
import { BankAdminDashboard } from './components/bank-admin-dashboard/bank-admin-dashboard';
import { BankAdminHome } from './components/bank-admin-home/bank-admin-home';
import { ApproveOrganizations } from './components/approve-organizations/approve-organizations';


@NgModule({
  declarations: [
    BankAdminDashboard,
    BankAdminHome,
    ApproveOrganizations
  ],
  imports: [
    CommonModule,
    BankAdminRoutingModule
  ]
})
export class BankAdminModule { }
