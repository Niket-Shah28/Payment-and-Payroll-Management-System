import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProfileResponseDto } from '../dto/profile-response-dto';
import { LoginService } from '../../auth/service/login-service'; 

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private baseUrl = 'http://localhost:8080/employee';

  constructor(private http: HttpClient, private loginService: LoginService) {}

  getProfile(): Observable<ProfileResponseDto> {
    const token = this.loginService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<ProfileResponseDto>(`${this.baseUrl}/profile`, { headers });
  }

}
