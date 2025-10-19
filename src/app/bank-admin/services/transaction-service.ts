import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TransactionDetailsDto } from '../dto/transaction-details.model'; 
import { Page } from '../dto/page.model'; 

@Injectable({
  providedIn: 'root'
})
export class TransactionsService {

  private readonly API_BASE = 'http://localhost:8080/banks';

  constructor(private http: HttpClient) {}

  /**
   * Fetch transactions with optional filters and pagination.
   * dateFrom/dateTo should be ISO strings (e.g. '2025-10-19T00:00:00').
   */
  getTransactions(
    entityName?: string,
    dateFrom?: string,
    dateTo?: string,
    pageNumber: number = 0,
    pageSize: number = 10
  ): Observable<Page<TransactionDetailsDto>> {

    let params = new HttpParams()
      .set('pageNumber', String(pageNumber))
      .set('pageSize', String(pageSize));

    if (entityName) params = params.set('entityName', entityName);
    if (dateFrom) params = params.set('dateFrom', dateFrom);
    if (dateTo) params = params.set('dateTo', dateTo);

    // ✅ Add JWT token from local storage (or your auth service)
    const token = localStorage.getItem('authToken'); // adjust key if different
    const headers = new HttpHeaders({
      Authorization: token ? `Bearer ${token}` : ''
    });

    return this.http.get<Page<TransactionDetailsDto>>(`${this.API_BASE}/transactions`, {
      params,
      headers
    });
  }
}
