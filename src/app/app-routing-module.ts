import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path:'',
    loadChildren:()=>
      import("./auth/auth-module").then(m=>m.AuthModule)
  },
  {
    path:'organization',
    loadChildren:()=>
      import("./organization/organization-module").then(m=>m.OrganizationModule)
  },
  {
    path:'bank-admin',
    loadChildren:()=>
      import("./bank-admin/bank-admin-module").then(m=>m.BankAdminModule)
  },
  {
    path:'employee',
    loadChildren:()=>
      import("./employee/employee-module").then(m=>m.EmployeeModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
