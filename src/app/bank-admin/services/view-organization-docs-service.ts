import { Component, OnInit } from '@angular/core';
import { OrganizationInfoDto } from '../dto/organization-info-dto'; 
import { OrganizationDocumentDto } from '../dto/organzization-document-dto'; 
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ViewOrganizationDocsService {

  private baseUrl = 'http://localhost:8080/banks/organizations';

  constructor(private http: HttpClient) {}

  // Get paginated organizations
  getAllOrganizations(pageNumber: number = 0, pageSize: number = 10): Observable<any> {
    return this.http.get(`${this.baseUrl}/info?pageNumber=${pageNumber}&pageSize=${pageSize}`);
  }

  // Get documents for one organization
  getDocumentsByOrganizationId(organizationId: number): Observable<OrganizationDocumentDto[]> {
    return this.http.get<OrganizationDocumentDto[]>(`${this.baseUrl}/${organizationId}/documents`);
  }

  // View document in browser
  viewDocument(orgId: number, docId: number): void {
    window.open(`${this.baseUrl}/${orgId}/documents/${docId}/view`, '_blank');
  }

  //Download document
  downloadDocument(orgId: number, docId: number): void {
    const link = document.createElement('a');
    link.href = `${this.baseUrl}/${orgId}/documents/${docId}/download`;
    link.target = '_blank';
    link.rel = 'noopener';
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  }

//   // Remove the previous download logic
// downloadDocument(orgId: number, docId: number): void {
//     // simply open backend download URL
//     window.open(`${this.baseUrl}/${orgId}/documents/${docId}/download`, '_blank');
// }


  
}
