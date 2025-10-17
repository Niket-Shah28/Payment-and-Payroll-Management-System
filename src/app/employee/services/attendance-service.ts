// services/attendance.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AttendanceMarkRequest } from '../dto/attendance-mark-request.model';
import { AttendanceResponseDto } from '../dto/attendance-response.model';
import { LoginService } from '../../auth/service/login-service';

@Injectable({
  providedIn: 'root'
})
export class AttendanceService {

  private baseUrl = 'http://localhost:8080/employee/attendance';

  constructor(private http: HttpClient, private loginService: LoginService) { }

  private getHeaders(): HttpHeaders {
    const token = this.loginService.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  markAttendance(request: AttendanceMarkRequest): Observable<AttendanceResponseDto[]> {
    return this.http.post<AttendanceResponseDto[]>(`${this.baseUrl}/mark`, request, { headers: this.getHeaders() });
  }

  getAttendance(from: string, to: string): Observable<AttendanceResponseDto[]> {
    return this.http.get<AttendanceResponseDto[]>(`${this.baseUrl}?from=${from}&to=${to}`, { headers: this.getHeaders() });
  }
}
