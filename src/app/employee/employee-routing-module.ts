import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EmployeeDashboard } from './components/employee-dashboard/employee-dashboard';
import { HomeComponent } from './components/employee-home/home-component/home-component';

const routes: Routes = [
  {
    path:'dashboard',
    component:EmployeeDashboard,
    children: [
      { path: 'home', component: HomeComponent },
      { path: '', redirectTo: 'home', pathMatch: 'full' },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmployeeRoutingModule { }
