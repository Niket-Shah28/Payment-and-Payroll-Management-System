import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BankAdminDashboard } from './components/bank-admin-dashboard/bank-admin-dashboard';

const routes: Routes = [
  {
    path:'dashboard',
    component:BankAdminDashboard
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BankAdminRoutingModule { }
