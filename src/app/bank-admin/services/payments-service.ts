import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Status } from '../dto/status';



@Injectable({
  providedIn: 'root'
})
export class PaymentsService {

  private baseUrl = 'http://localhost:8080/banks/payments';

  constructor(private http: HttpClient) { }

  getPaymentRequests(pageNumber: number, pageSize: number): Observable<any> {
    let params = new HttpParams()
      .set('pageNumber', pageNumber)
      .set('pageSize', pageSize);
    return this.http.get(`${this.baseUrl}/requests`, { params });
  }

 updatePaymentStatus(paymentRequestId: number, status: Status): Observable<any> {
    return this.http.post("http://localhost:8080/organization/paymentRequest/"+paymentRequestId,null, {
      params: new HttpParams().set('status', status)
    });
  }
}
