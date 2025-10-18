import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BankAdminDashboard } from './components/bank-admin-dashboard/bank-admin-dashboard';
import { BankAdminHome } from './components/bank-admin-home/bank-admin-home';
import { ApproveOrganizations } from './components/approve-organizations/approve-organizations';

const routes: Routes = [
  {
    path:'dashboard',
    component:BankAdminDashboard,
    children: [
      { path: 'home', component: BankAdminHome },
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'approve-organizations', component: ApproveOrganizations }

    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BankAdminRoutingModule { }
