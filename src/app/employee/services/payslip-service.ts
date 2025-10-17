import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PayslipDetail } from '../dto/payslip-detail.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PayslipService {

  private baseUrl = 'http://localhost:8080/employee/payslip';

  constructor(private http: HttpClient) {}

  getPayslip(month: string, year: number): Observable<PayslipDetail> {
    return this.http.get<PayslipDetail>(`${this.baseUrl}/month/${month}/year/${year}`);
  }

  downloadPayslip(month: string, year: number): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/download/month/${month}/year/${year}`, {
      responseType: 'blob',
    });
  }
  
}
