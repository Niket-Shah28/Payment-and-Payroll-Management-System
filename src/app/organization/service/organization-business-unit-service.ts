import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BusinessUnitDataDto } from '../dto/BusinessUnitDataDto';

@Injectable({
  providedIn: 'root'
})
export class OrganizationBusinessUnitService {
  constructor(private http:HttpClient){}

  businessUnitRequestUri:string="http://localhost:8080/organization/businessUnits";

  getBusinessUnits():Observable<BusinessUnitDataDto>{
    return this.http.get<BusinessUnitDataDto>(this.businessUnitRequestUri);
  }

  updateBusinessUnit(data:BusinessUnitDataDto):Observable<void>{
    const payload = new FormData();
    payload.append("businessUnitName", data.businessUnitName)
    return this.http.patch<void>(this.businessUnitRequestUri+"/"+data.businessUnitId, payload);
  }

  addBusinessUnit(data:string):Observable<void>{
    const payload = new FormData();
    payload.append("businessUnitName", data)
    return this.http.post<void>(this.businessUnitRequestUri, payload);
  }

  deleteBusinessUnit(businessUnitId:Number):Observable<void>{
    return this.http.delete<void>(this.businessUnitRequestUri+"/"+businessUnitId);
  }
}
