import { Component, OnInit } from '@angular/core';
import { EmployeeDocumentService } from '../../services/employee-document-service';
import { DocumentDto } from '../../dto/employee-document-dto';
import { DocumentTypeDto } from '../../dto/employee-document-dto';


@Component({
  selector: 'app-employee-documents',
  standalone: false,
  templateUrl: './employee-documents.html',
  styleUrls: ['./employee-documents.css']
})
export class EmployeeDocuments implements OnInit{

  documentTypes: DocumentTypeDto[] = [];
  uploadedDocuments: DocumentDto[] = [];

  selectedFile: File | null = null;
  selectedDocumentTypeId: number | null = null;
  organizationId = 1; // Replace with logged-in employee's org ID from token or service
  fileError: string | null = null;
  isUploading = false;

  constructor(private docService: EmployeeDocumentService) {}

  ngOnInit() {
    this.loadDocumentTypes();
    this.loadEmployeeDocuments();
  }

  loadDocumentTypes() {
    this.docService.getActiveDocumentTypes().subscribe({
      next: (res) => (this.documentTypes = res),
      error: (err) => console.error('Failed to fetch document types', err)
    });
  }

  loadEmployeeDocuments() {
    this.docService.getEmployeeDocuments().subscribe({
      next: (res) => (this.uploadedDocuments = res),
      error: (err) => console.error('Failed to fetch documents', err)
    });
  }

  onFileChange(event: Event) {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0];
    this.fileError = null;

    if (!file) return;

    const allowedTypes = ['application/pdf', 'image/jpeg', 'image/png'];
    if (!allowedTypes.includes(file.type)) {
      this.fileError = 'Only PDF, JPG, or PNG files allowed.';
      input.value = '';
      return;
    }

    this.selectedFile = file;
  }

  uploadDocument() {
    if (!this.selectedFile || !this.selectedDocumentTypeId) {
      this.fileError = 'Select document type and file first.';
      return;
    }

    this.isUploading = true;
    this.docService.uploadDocument(this.selectedDocumentTypeId, this.selectedFile, this.organizationId).subscribe({
      next: () => {
        alert('Document uploaded successfully!');
        this.isUploading = false;
        this.selectedFile = null;
        this.selectedDocumentTypeId = null;
        this.loadEmployeeDocuments();
      },
      error: (err) => {
        console.error(err);
        this.isUploading = false;
        alert('Upload failed. Please try again.');
      }
    });
  }

  viewDocument(url: string) {
    this.docService.viewDocument(url);
  }

  downloadDocument(url: string, name: string) {
    this.docService.downloadDocument(url, name);
  }

}
