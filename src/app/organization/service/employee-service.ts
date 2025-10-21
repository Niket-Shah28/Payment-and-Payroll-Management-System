import { Injectable } from '@angular/core';
import { EmployeePageResponse } from '../dto/EmployeePageResponse';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  constructor(private http:HttpClient){}

  apiBaseUrl="http://localhost:8080/organization/employees";

  getEmployees(page: number, size: number, search: string): Observable<EmployeePageResponse> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    if (search && search.trim()) {
      params = params.set('search', search.trim());
    }

    return this.http.get<EmployeePageResponse>(this.apiBaseUrl+"/page", { params });
  }

  uploadFile(file:File):Observable<void>{
    console.log("UPLOADING")
    const payload = new FormData();
    payload.append("file", file);
    return this.http.post<void>("http://localhost:8080/organization/employees", payload);
  }
}
