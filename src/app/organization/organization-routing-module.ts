import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OrganizationDepartments } from './components/organization-departments/organization-departments';
import { OrganizationLayoutComponent } from './components/organization-layout-component/organization-layout-component';
import { HomePage } from './components/home-page/home-page';
import { OrganizationBusinessUnits } from './components/organization-business-units/organization-business-units';
import { OrganizationEmployeeDesignations } from './components/organization-employee-designations/organization-employee-designations';
import { BankAccount } from './components/bank-account/bank-account';
import { OrganizationVendors } from './components/organization-vendors/organization-vendors';
import { OrganizationPayoutComponent } from './components/organization-payout-component/organization-payout-component';

const routes: Routes = [
  {
    path:'dashboard',
    component:OrganizationLayoutComponent,
    children:[
      {
        path:'home',
        component:HomePage
      },
      {
        path:'departments',
        component:OrganizationDepartments
      },
      {
        path:'business-units',
        component:OrganizationBusinessUnits
      },
      {
        path:'roles',
        component:OrganizationEmployeeDesignations
      },
      {
        path:'bank-account',
        component:BankAccount
      },
      {
        path:'vendors',
        component:OrganizationVendors
      },
      {
        path:'initiate-payout',
        component:OrganizationPayoutComponent
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OrganizationRoutingModule { }
