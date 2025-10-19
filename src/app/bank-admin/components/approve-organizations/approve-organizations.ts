import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApproveOrganizationService } from '../../services/approve-organization-service';
import { OrganizationRequestDetailsDto } from '../../dto/organization-request-details-dto';
import { OrganizationRequestDocumentsResponseDto } from '../../dto/organization-request-documents-response-dto';

@Component({
  selector: 'app-approve-organizations',
  standalone: false,
  templateUrl: './approve-organizations.html',
  styleUrls: ['./approve-organizations.css']
})
export class ApproveOrganizations implements OnInit, OnDestroy {

  pendingRequests: OrganizationRequestDetailsDto[] = [];
  loadingRequests = false;
  errorMsg = '';

  selectedRequestId: number | null = null;
  documents: OrganizationRequestDocumentsResponseDto[] = [];
  loadingDocuments = false;

  viewingBlobUrl: string | null = null;
  viewingMimeType: string | null = null;
  viewingDocumentId: number | null = null;

  constructor(
    private orgService: ApproveOrganizationService,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.loadPendingRequests();
  }

  // Load pending organization requests
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

  // Load documents for selected request
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

  // Approve or reject an organization request
  processRequest(requestId: number, status: 'APPROVED' | 'REJECTED') {
    if (!confirm(`${status} organization request ${requestId}?`)) return;

    this.orgService.processOrganizationRequest(requestId, status).subscribe({
      next: () => {
        this.loadPendingRequests();
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

  processRequestFromDocuments(status: 'APPROVED' | 'REJECTED') {
    if (!this.selectedRequestId) return;
    this.processRequest(this.selectedRequestId, status);
  }

  // View document in a new tab (Cloudinary URL)
  onView(document: OrganizationRequestDocumentsResponseDto) {
    const fileUrl = document.cloudinaryUrl;
    if (!fileUrl) {
      console.error('Document URL missing!');
      return;
    }
    window.open(fileUrl, '_blank'); // opens Cloudinary file directly
  }

  // Download document using HttpClient + Blob
  onDownload(document: OrganizationRequestDocumentsResponseDto) {
    const fileUrl = document.cloudinaryUrl;
    if (!fileUrl) {
      console.error('Document URL missing!');
      return;
    }

    const ext = this.getFileExtension(fileUrl);
    const filename = `${document.documentTypeName || 'document'}.${ext}`;

    // Fetch the file as a Blob
    this.http.get(fileUrl, { responseType: 'blob' }).subscribe({
      next: (blob) => this.downloadBlob(blob, filename),
      error: (err) => console.error('Failed to download document', err)
    });
  }

  // Utility to trigger browser download from Blob
  private downloadBlob(blob: Blob, filename: string) {
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = filename;
    a.click();
    URL.revokeObjectURL(url);
  }

  // Extract file extension from URL
  private getFileExtension(url: string): string {
    const match = url.split('.').pop();
    return match ? match.split('?')[0] : 'pdf';
  }

  // Close document viewer panel
  closeDocumentsPanel() {
    this.selectedRequestId = null;
    this.documents = [];
    this.revokeViewingUrl();
  }

  // Clean up Blob URLs
  private revokeViewingUrl() {
    if (this.viewingBlobUrl) {
      URL.revokeObjectURL(this.viewingBlobUrl);
      this.viewingBlobUrl = null;
      this.viewingMimeType = null;
      this.viewingDocumentId = null;
    }
  }

  ngOnDestroy(): void {
    this.revokeViewingUrl();
  }
}
