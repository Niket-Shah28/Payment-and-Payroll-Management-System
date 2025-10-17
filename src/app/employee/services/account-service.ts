import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BankAccountDetailsDto } from '../dto/bank-account-details-dto';
import { BankAccountDetailsUpdateDto } from '../dto/bank-account-details-update-dto'; 
import { EmployeeBankInfoDto } from '../dto/employee-bank-info-dto';
import { EmployeeBankInfoUpdateDto } from '../dto/employee-bank-info-update-dto'; 
import { LoginService } from '../../auth/service/login-service';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private baseUrlAccount = 'http://localhost:8080/employee/bank-account';
  private baseUrlBankInfo = 'http://localhost:8080/employee/bank-info';

  constructor(private http: HttpClient, private loginService: LoginService) {}

  private getHeaders(): HttpHeaders {
    const token = this.loginService.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  // --- Bank Account ---
  getAllAccounts(): Observable<BankAccountDetailsDto[]> {
    return this.http.get<BankAccountDetailsDto[]>(this.baseUrlAccount, { headers: this.getHeaders() });
  }

  getAccountById(accountId: number): Observable<BankAccountDetailsDto> {
    return this.http.get<BankAccountDetailsDto>(`${this.baseUrlAccount}/${accountId}`, { headers: this.getHeaders() });
  }

 updateAccount(accountId: number, updateDto: BankAccountDetailsUpdateDto): Observable<BankAccountDetailsDto> {
  return this.http.patch<BankAccountDetailsDto>(`${this.baseUrlAccount}/${accountId}`, updateDto, { headers: this.getHeaders() });
}

  getActiveAccounts(): Observable<BankAccountDetailsDto[]> {
    return this.http.get<BankAccountDetailsDto[]>(`${this.baseUrlAccount}/active`, { headers: this.getHeaders() });
  }

  // --- Employee Bank Info ---
  getAllBankInfo(): Observable<EmployeeBankInfoDto[]> {
    return this.http.get<EmployeeBankInfoDto[]>(this.baseUrlBankInfo, { headers: this.getHeaders() });
  }

  getBankInfoById(bankInfoId: number): Observable<EmployeeBankInfoDto> {
    return this.http.get<EmployeeBankInfoDto>(`${this.baseUrlBankInfo}/${bankInfoId}`, { headers: this.getHeaders() });
  }

  updateBankInfo(bankInfoId: number, updateDto: EmployeeBankInfoUpdateDto): Observable<EmployeeBankInfoDto> {
    return this.http.patch<EmployeeBankInfoDto>(`${this.baseUrlBankInfo}/${bankInfoId}`, updateDto, { headers: this.getHeaders() });
  }

  getActiveBankInfo(): Observable<EmployeeBankInfoDto[]> {
    return this.http.get<EmployeeBankInfoDto[]>(`${this.baseUrlBankInfo}/active`, { headers: this.getHeaders() });
  }
  
}
