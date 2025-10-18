import { Injectable } from '@angular/core';
import { BankAccountRequestDto } from '../dto/BankAccountRequestDto';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BankAccountDto } from '../dto/BankAccountDto';

@Injectable({
  providedIn: 'root'
})
export class OrganizationBankAccountService {
  constructor(private http:HttpClient){}

  bankRequestUri="http://localhost:8080/organization/bankAccount"

  getAccount():Observable<BankAccountDto>{
    return this.http.get<BankAccountDto>(this.bankRequestUri);
  }

  addAccount(dto:BankAccountRequestDto):Observable<void>{
    return this.http.post<void>(this.bankRequestUri, dto);
  }

  updateAccount(dto:BankAccountDto):Observable<void>{
    const payload = {
      "accountNumber":dto.accountNumber,
      "accountHolderName":dto.accountHolderName,
      "ifscCode":dto.ifscCode,
      "accountType":dto.accountType,
      "balance":dto.balance
    }
    return this.http.patch<void>(this.bankRequestUri+"/"+dto.accountId, payload);
  }

  deleteAccount(accountId:Number):Observable<void>{
    return this.http.delete<void>(this.bankRequestUri+"/"+accountId);
  }

  depositAmount(accountId:Number, amount:Number):Observable<void>{
    let payload = new FormData();
    payload.append("amount", amount.toString());
    return this.http.post<void>(this.bankRequestUri+"/"+accountId+"/deposit", payload);
  }

}
