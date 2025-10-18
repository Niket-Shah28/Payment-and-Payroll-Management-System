import { Component, OnInit } from '@angular/core';
import { ApproveOrganizationService } from '../../services/approve-organization-service'; 
import { OrganizationRequestDetailsDto } from '../../dto/organization-request-details-dto'; 
import { OrganizationRequestDocumentsResponseDto } from '../../dto/organization-request-documents-response-dto';

@Component({
  selector: 'app-approve-organizations',
  standalone: false,
  templateUrl: './approve-organizations.html',
  styleUrl: './approve-organizations.css'
})
export class ApproveOrganizations {

  pendingRequests: OrganizationRequestDetailsDto[] = [];
  loadingRequests = false;
  errorMsg = '';

  // documents panel
  selectedRequestId: number | null = null;
  documents: OrganizationRequestDocumentsResponseDto[] = [];
  loadingDocuments = false;

  // viewing a document
  viewingBlobUrl: string | null = null;
  viewingMimeType: string | null = null;
  viewingDocumentId: number | null = null;

  constructor(private orgService: ApproveOrganizationService) {}

  ngOnInit(): void {
    this.loadPendingRequests();
  }

  loadPendingRequests(): void {
    this.loadingRequests = true;
    this.orgService.getPendingRequests().subscribe({
      next: (res) => {
        this.pendingRequests = res;
        this.loadingRequests = false;
      },
      error: (err) => {
        this.errorMsg = 'Failed to fetch pending requests';
        console.error(err);
        this.loadingRequests = false;
      }
    });
  }

  openDocuments(requestId: number): void {
    this.selectedRequestId = requestId;
    this.documents = [];
    this.loadingDocuments = true;
    this.orgService.getDocumentsList(requestId).subscribe({
      next: (list) => {
        this.documents = list;
        this.loadingDocuments = false;
      },
      error: (err) => {
        this.errorMsg = 'Failed to fetch documents';
        console.error(err);
        this.loadingDocuments = false;
      }
    });
  }

  // Approve or Reject from the list/table
  processRequest(requestId: number, status: 'APPROVED' | 'REJECTED') {
    if (!confirm(`${status} organization request ${requestId}?`)) return;
    this.orgService.processOrganizationRequest(requestId, status).subscribe({
      next: () => {
        // refresh
        this.loadPendingRequests();
        // if user was viewing this request's documents, update state
        if (this.selectedRequestId === requestId) {
          this.selectedRequestId = null;
          this.documents = [];
        }
      },
      error: (err) => {
        this.errorMsg = `Could not ${status.toLowerCase()} request`;
        console.error(err);
      }
    });
  }

  // Approve or Reject while viewing documents
  processRequestFromDocuments(status: 'APPROVED' | 'REJECTED') {
    if (!this.selectedRequestId) return;
    this.processRequest(this.selectedRequestId, status);
  }

  // View document in-browser using blob -> objectURL
  viewDocument(requestId: number, documentId: number) {
    // clear previous view
    this.revokeViewingUrl();
    this.viewingDocumentId = documentId;
    this.orgService.viewDocument(requestId, documentId).subscribe({
      next: (blob) => {
        const mime = blob.type || this.inferMimeFromDocumentName(this.documents.find(d => d.requestDocumentId === documentId)?.documentTypeName);
        this.viewingMimeType = mime;
        this.viewingBlobUrl = URL.createObjectURL(blob);
        // For PDFs and images the iframe/img will render using viewingBlobUrl
      },
      error: (err) => {
        this.errorMsg = 'Failed to fetch document for viewing';
        console.error(err);
      }
    });
  }

  // Download document
  downloadDocument(requestId: number, documentId: number, documentName?: string) {
    this.orgService.downloadDocument(requestId, documentId).subscribe({
      next: (blob) => {
        const filename = documentName ? `${documentName}_${documentId}` : `document_${documentId}`;
        this.downloadBlob(blob, filename);
      },
      error: (err) => {
        this.errorMsg = 'Failed to download document';
        console.error(err);
      }
    });
  }

  // utility to create anchor and download blob
  private downloadBlob(blob: Blob, filename: string) {
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    // try to use Content-Type to decide extension if none provided
    a.download = filename;
    document.body.appendChild(a);
    a.click();
    a.remove();
    URL.revokeObjectURL(url);
  }

  private inferMimeFromDocumentName(name?: string): string {
    if (!name) return 'application/octet-stream';
    const n = name.toLowerCase();
    if (n.endsWith('.pdf') || n.includes('pdf')) return 'application/pdf';
    if (n.includes('jpg') || n.includes('jpeg')) return 'image/jpeg';
    if (n.includes('png')) return 'image/png';
    return 'application/octet-stream';
  }

  closeDocumentsPanel() {
    this.selectedRequestId = null;
    this.documents = [];
    this.revokeViewingUrl();
  }

  private revokeViewingUrl() {
    if (this.viewingBlobUrl) {
      URL.revokeObjectURL(this.viewingBlobUrl);
      this.viewingBlobUrl = null;
      this.viewingMimeType = null;
      this.viewingDocumentId = null;
    }
  }

  // cleanup on destroy
  ngOnDestroy(): void {
    this.revokeViewingUrl();
  }

}
