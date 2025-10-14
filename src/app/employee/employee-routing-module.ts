import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EmployeeDashboard } from './components/employee-dashboard/employee-dashboard';

const routes: Routes = [
  {
    path:'dahsboard',
    component:EmployeeDashboard
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmployeeRoutingModule { }
