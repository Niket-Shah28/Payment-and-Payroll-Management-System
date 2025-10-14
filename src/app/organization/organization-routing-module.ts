import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OrganizationDashboard } from './components/organization-dashboard/organization-dashboard';

const routes: Routes = [
  {
    path:'dashboard',
    component:OrganizationDashboard
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OrganizationRoutingModule { }
