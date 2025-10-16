import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EmployeeRoutingModule } from './employee-routing-module';
import { EmployeeDashboard } from './components/employee-dashboard/employee-dashboard';
import { HttpClientModule } from '@angular/common/http';
import { HomeComponent } from './components/employee-home/home-component/home-component';
import { EmployeeProfile } from './components/employee-profile/employee-profile';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { RaiseTicket } from './components/employee-tickets/raise-ticket/raise-ticket';
import { TicketList } from './components/employee-tickets/ticket-list/ticket-list';
import { TicketDetail } from './components/employee-tickets/ticket-detail/ticket-detail';
import { AccountService } from './services/account-service';
import { BankAccountList } from './components/employee-account-details/bank-account-list/bank-account-list';
import { EmployeeBankInfoList } from './components/employee-bank-info-list/employee-bank-info-list';


@NgModule({
  declarations: [
    EmployeeDashboard,
    HomeComponent,
    EmployeeProfile,
    RaiseTicket,
    TicketList,
    TicketDetail,
    BankAccountList,
    EmployeeBankInfoList
    
  ],
  imports: [
    CommonModule,
    EmployeeRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class EmployeeModule { }
