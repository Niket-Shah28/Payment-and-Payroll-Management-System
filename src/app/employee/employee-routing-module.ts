import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EmployeeDashboard } from './components/employee-dashboard/employee-dashboard';
import { HomeComponent } from './components/employee-home/home-component/home-component';
import { EmployeeProfile } from './components/employee-profile/employee-profile';
import { RaiseTicket} from './components/employee-tickets/raise-ticket/raise-ticket';
import { TicketList } from './components/employee-tickets/ticket-list/ticket-list';
import { TicketDetail } from './components/employee-tickets/ticket-detail/ticket-detail';
import { BankAccountList } from './components/employee-account-details/bank-account-list/bank-account-list';
import { EmployeeBankInfoList } from './components/employee-bank-info-list/employee-bank-info-list';
import { MarkAttendance } from './components/mark-attendance/mark-attendance/mark-attendance';


const routes: Routes = [
  {
    path:'dashboard',
    component:EmployeeDashboard,
    children: [
      { path: 'home', component: HomeComponent },
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'raise-tickets', component: RaiseTicket },
      { path: 'tickets', component: TicketList },
       { path: 'tickets/:ticketId', component: TicketDetail },
      {path: 'profile', component: EmployeeProfile},
      { path: 'bank-account-list', component: BankAccountList },
    { path: 'bank-account-list/:accountId', component: BankAccountList },
    { path: 'employee-bank-info-list', component: EmployeeBankInfoList },
    { path: 'employee-bank-info-list/:bankInfoId', component: EmployeeBankInfoList } ,
    {path: 'mark-attendance', component: MarkAttendance}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmployeeRoutingModule { }
