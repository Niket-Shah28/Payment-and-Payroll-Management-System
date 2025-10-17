import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DepartmentDataDto} from '../dto/DepartmentDataDto';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class OrganizationDepartmentService {
  constructor(private http:HttpClient){}

  departmentRequestUri:string="http://localhost:8080/organization/departments";

  getDepartments():Observable<DepartmentDataDto>{
    return this.http.get<DepartmentDataDto>(this.departmentRequestUri);
  }

  updateDepartment(data:DepartmentDataDto):Observable<void>{
    const payload = new FormData();
    payload.append("departmentName", data.departmentName)
    return this.http.patch<void>(this.departmentRequestUri+"/"+data.departmentId, payload);
  }

  addDepartment(data:string):Observable<void>{
    const payload = new FormData();
    payload.append("departmentName", data)
    return this.http.post<void>(this.departmentRequestUri, payload);
  }

  deleteDepartment(departmentId:Number):Observable<void>{
    return this.http.delete<void>(this.departmentRequestUri+"/"+departmentId);
  }
}
