import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrganizationPayoutService {
  constructor(private http:HttpClient){}

  paymentRequestUri = "http://localhost:8080/organization/payment/requests";

  addRequest(paymentData:any):Observable<void>{
    return this.http.post<void>(this.paymentRequestUri, paymentData);
  }
}
