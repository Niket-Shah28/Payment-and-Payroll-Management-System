import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class OrganizationCoreDataService {
  constructor(private http:HttpClient){}
  private baseUri = "http://localhost:8080/organization/core";

  getCoreData():Observable<any>{
    return this.http.get<any>(`${this.baseUri}`);
  }
}
