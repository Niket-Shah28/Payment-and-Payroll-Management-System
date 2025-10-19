import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TransactionDetailsDto } from '../dto/transaction-details-dto';

@Injectable({
  providedIn: 'root'
})
export class BankTransactionService {

  private baseUrl = 'http://localhost:8080/banks/transactions';

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('jwtToken');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  getTransactions(
    entityName?: string,
    dateFrom?: string,
    dateTo?: string,
    pageNumber: number = 0,
    pageSize: number = 10
  ): Observable<any> {
    let params = new HttpParams()
      .set('pageNumber', pageNumber)
      .set('pageSize', pageSize);

    if (entityName) params = params.set('entityName', entityName);
    if (dateFrom) params = params.set('dateFrom', dateFrom);
    if (dateTo) params = params.set('dateTo', dateTo);

    return this.http.get<any>(this.baseUrl, {
      headers: this.getHeaders(),
      params
    });
  }
  
}
