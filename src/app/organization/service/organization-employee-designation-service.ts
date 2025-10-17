import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { EmployeeRoleDto } from '../dto/EmployeeRoleDto';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class OrganizationEmployeeDesignationService {
  constructor(private http:HttpClient){}
  
  RoleRequestUri:string="http://localhost:8080/organization/roles";
  
  getRoles():Observable<EmployeeRoleDto>{
    return this.http.get<EmployeeRoleDto>(this.RoleRequestUri);
  }

  updateRole(data:any):Observable<void>{
    const payload = new FormData();
    payload.append("role", data.role)
    return this.http.patch<void>(this.RoleRequestUri+"/"+data.roleId, payload);
  }

  addRole(data:string):Observable<void>{
    const payload = new FormData();
    payload.append("role", data)
    return this.http.post<void>(this.RoleRequestUri, payload);
  }

  deleteRole(roleId:Number):Observable<void>{
    return this.http.delete<void>(this.RoleRequestUri+"/"+roleId);
  }
}
