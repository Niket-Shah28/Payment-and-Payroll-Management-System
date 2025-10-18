import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoginService } from '../../auth/service/login-service';

@Injectable({
  providedIn: 'root'
})
export class BankAdminService {

  private baseUrl = 'http://localhost:8080/banks'

  constructor(private http: HttpClient, private loginService: LoginService) {}

  
}
