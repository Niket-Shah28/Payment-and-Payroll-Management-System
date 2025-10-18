import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { OrganizationRequestDetailsDto } from '../dto/organization-request-details-dto';
import { OrganizationRequestDocumentsResponseDto } from '../dto/organization-request-documents-response-dto'; 

const API_BASE = 'http://localhost:8080/organization/requests';

@Injectable({
  providedIn: 'root'
})
export class ApproveOrganizationService {

  

  constructor(private http: HttpClient) {}

  // 1. Get pending organization requests
  getPendingRequests(): Observable<OrganizationRequestDetailsDto[]> {
    return this.http.get<OrganizationRequestDetailsDto[]>(`${API_BASE}/pending`);
  }

  // 2. Get documents list for a request
  getDocumentsList(requestId: number): Observable<OrganizationRequestDocumentsResponseDto[]> {
    return this.http.get<OrganizationRequestDocumentsResponseDto[]>(`${API_BASE}/${requestId}/documents`);
  }

  // 3. Approve or reject request (PUT /{requestId}?status=APPROVED)
  processOrganizationRequest(requestId: number, status: 'APPROVED' | 'REJECTED'): Observable<void> {
    const params = new HttpParams().set('status', status);
    return this.http.put<void>(`${API_BASE}/${requestId}`, null, { params });
  }

  // 4. View document as blob (stream)
  viewDocument(requestId: number, documentId: number): Observable<Blob> {
    const url = `${API_BASE}/${requestId}/documents/${documentId}/view`;
    return this.http.get(url, { responseType: 'blob' });
  }

  // 5. Download document as blob
  downloadDocument(requestId: number, documentId: number): Observable<Blob> {
    const url = `${API_BASE}/${requestId}/documents/${documentId}/download`;
    return this.http.get(url, { responseType: 'blob' });
  }
  
}
