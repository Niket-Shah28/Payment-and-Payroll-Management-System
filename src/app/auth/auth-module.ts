import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing-module';
import { ReactiveFormsModule } from '@angular/forms';
import { Login } from './components/login/login';
import { HttpClientModule } from '@angular/common/http';


@NgModule({
  declarations: [
    Login
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ]
})
export class AuthModule { }
