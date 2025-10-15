import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EmployeeDto } from '../dto/employee-dto';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

   private apiUrl = 'http://localhost:8080/auth/login';

  constructor(private http: HttpClient) { }

 getEmployeeReferenceId(): Observable<EmployeeDto> {
    // When you implement authentication, you will add token headers here.
    return this.http.get<EmployeeDto>(this.apiUrl);
  }
  
}
