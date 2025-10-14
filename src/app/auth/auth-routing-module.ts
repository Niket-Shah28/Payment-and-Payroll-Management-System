import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Login } from './components/login/login';
import { OrganizationModule } from '../organization/organization-module';
import { AuthModule } from './auth-module';

const routes: Routes = [
  {
    path:'',
    component:Login
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
