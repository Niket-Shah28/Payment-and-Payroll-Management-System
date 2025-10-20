import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, switchMap } from 'rxjs';
import { environment } from '../../../environments/environment'; 
import { DocumentDto, DocumentTypeDto } from '../dto/employee-document-dto';
import { CloudinaryUploadService } from '../../universal_services/cloudinary-upload-service'; 

@Injectable({
  providedIn: 'root'
})
export class EmployeeDocumentService {

   private baseUrl = `${environment.apiBaseUrl}/employee`;

  constructor(
    private http: HttpClient,
    private cloudinaryService: CloudinaryUploadService
  ) {}

  getActiveDocumentTypes(): Observable<DocumentTypeDto[]> {
    return this.http.get<DocumentTypeDto[]>(`${this.baseUrl}/documents/types`);
  }

  getEmployeeDocuments(): Observable<DocumentDto[]> {
    return this.http.get<DocumentDto[]>(`${this.baseUrl}/documents`);
  }

  uploadDocument(documentTypeId: number, file: File, organizationId: number): Observable<DocumentDto> {
    return this.cloudinaryService.uploadFile(file).pipe(
      switchMap((uploadedUrl: string) => {
        const fileFormat = file.name.split('.').pop()?.toLowerCase() as 'pdf' | 'jpg' | 'png';
        const payload: DocumentDto = {
          documentTypeId,
          cloudinaryUrl: uploadedUrl,
          documentSize: file.size,
          fileFormat
        };
        return this.http.post<DocumentDto>(
  `${this.baseUrl}/documents`, 
  payload
);

      })
    );
  }

  viewDocument(url: string): void {
    window.open(url, '_blank');
  }

  downloadDocument(url: string, fileName: string): void {
    fetch(url)
      .then(res => res.blob())
      .then(blob => {
        const blobUrl = URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = blobUrl;
        link.download = fileName;
        link.click();
        URL.revokeObjectURL(blobUrl);
      })
      .catch(err => console.error('Download failed:', err));
  }
  
}
