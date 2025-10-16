import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OrganizationDepartments } from './components/organization-departments/organization-departments';
import { OrganizationLayoutComponent } from './components/organization-layout-component/organization-layout-component';
import { HomePage } from './components/home-page/home-page';

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
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OrganizationRoutingModule { }
