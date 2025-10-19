import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BankAdminRoutingModule } from './bank-admin-routing-module';
import { BankAdminDashboard } from './components/bank-admin-dashboard/bank-admin-dashboard';
import { BankAdminHome } from './components/bank-admin-home/bank-admin-home';
import { ApproveOrganizations } from './components/approve-organizations/approve-organizations';
import { ViewOrganizationDocs } from './components/view-organization-docs/view-organization-docs';
import { ApprovePayments } from './components/approve-payments/approve-payments';
import { Transactions } from './components/transactions/transactions';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    BankAdminDashboard,
    BankAdminHome,
    ApproveOrganizations,
    ViewOrganizationDocs,
    Transactions,
    ApprovePayments
  ],
  imports: [
    CommonModule,
    BankAdminRoutingModule, FormsModule
  ]
})
export class BankAdminModule { }
