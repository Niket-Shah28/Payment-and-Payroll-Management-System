import { Component, OnInit } from '@angular/core';
import { OrganizationInfoDto } from '../../dto/organization-info-dto'; 
import { OrganizationDocumentDto } from '../../dto/organzization-document-dto'; 
import { ViewOrganizationDocsService } from '../../services/view-organization-docs-service';

@Component({
  selector: 'app-view-organization-docs',
  standalone: false,
  templateUrl: './view-organization-docs.html',
  styleUrl: './view-organization-docs.css'
})
export class ViewOrganizationDocs {

  organizations: OrganizationInfoDto[] = [];
  
  loading = false;
  errorMsg = '';

  selectedOrgId: number | null = null;
  documents: OrganizationDocumentDto[] = [];
  loadingDocuments = false;

  constructor(private orgService: ViewOrganizationDocsService) {}

  ngOnInit(): void {
    this.loadOrganizations();
  }

  loadOrganizations(): void {
    this.loading = true;
    this.orgService.getAllOrganizations().subscribe({
      next: (res) => {
        this.organizations = res.content;
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.errorMsg = 'Failed to load organizations';
        this.loading = false;
      }
    });
  }

  // viewDocuments(organizationId: number) {
  //   this.selectedOrgId = organizationId;
  //   this.documents = [];
  //   this.loadingDocuments = true;

  //   this.orgService.getDocumentsByOrganizationId(organizationId).subscribe({
  //     next: (res) => {
  //       this.documents = res;
  //       this.loadingDocuments = false;
  //     },
  //     error: (err) => {
  //       console.error(err);
  //       this.errorMsg = 'Failed to load documents';
  //       this.loadingDocuments = false;
  //     }
  //   });
  // }

  viewDocuments(organizationId: number){
   this.selectedOrgId = organizationId;
    this.documents = [];
    this.loadingDocuments = true;

    this.orgService.getDocumentsByOrganizationId(organizationId).subscribe({
      next: (res) => {
        this.documents = res;
        this.loadingDocuments = false;
      },
      error: (err) => {
        console.error(err);
        this.errorMsg = 'Failed to load documents';
        this.loadingDocuments = false;
      }
    });
  }

 onViewDocument(doc: OrganizationDocumentDto) {
    window.open(doc.cloudinaryUrl, '_blank');
}

// onDownloadDocument(doc: OrganizationDocumentDto) {
//     const link = document.createElement('a');
//     link.href = doc.cloudinaryUrl;
//     link.download = doc.documentTypeName; // optional: give default filename
//     link.target = '_blank';
//     link.click();
// }

onDownloadDocument(url?: string, title?: string) {
  if (!url) return this.handleError('No document URL provided.');

  // Extract file extension from URL
  const extensionMatch = url.split('.').pop()?.split(/\#|\?/)[0]; // handles query strings
  const extension = extensionMatch ? extensionMatch.toLowerCase() : 'pdf';

  // Fallback file name
  const fileName = (title ? title.replace(/\s/g, '_') : 'document') + '.' + extension;

  fetch(url)
    .then(res => {
      if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
      return res.blob();
    })
    .then(blob => {
      const blobUrl = URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = blobUrl;
      link.download = fileName;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
      URL.revokeObjectURL(blobUrl);
    })
    .catch(err => {
      console.error('Download failed', err);
      this.handleError('Could not download document. Check console for details.');
    });
}

handleError(message: string) { this.errorMsg = message; }


}
