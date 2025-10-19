import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { VendorRequestDto } from '../dto/VendorRequestDto';
import { catchError, map, Observable, tap, throwError } from 'rxjs';
import { VendorResponseDto } from '../dto/VendorResponseDto';
import { CloudinaryUploadService } from '../../universal_services/cloudinary-upload-service';

@Injectable({
  providedIn: 'root'
})
export class OrganizationVendorsService {
  constructor(private http: HttpClient, private cloudinaryService:CloudinaryUploadService) {}

  vendorRequestUri = "http://localhost:8080/organization/vendors";

  addVendor(vendorData: VendorRequestDto):Observable<void>{
    return this.http.post<void>(this.vendorRequestUri, vendorData);
  }

  getVendors():Observable<VendorResponseDto[]>{
    return this.http.get<VendorResponseDto[]>(this.vendorRequestUri);
  }
  updateVendor(){}

  deleteVendor(vendorId:Number){
    console.log(vendorId)
    return this.http.delete<void>(this.vendorRequestUri+"/"+vendorId);
  }

  getVendorFullProfile(vendorId:Number):Observable<VendorRequestDto>{
    return this.http.get<VendorRequestDto>(this.vendorRequestUri+"/"+vendorId);
  }

  uploadFile(file: File): Observable<string> {
  return this.cloudinaryService.uploadFile(file).pipe(
    tap((response: any) => console.log('Cloudinary Response:', response)), // ✅ just logs, doesn’t alter stream
    map((response: any) => response), // ✅ extracts URL properly
    catchError((error: Error) => {
      console.error('File upload failed:', error);
      return throwError(() => new Error(`File upload failed: ${error.message}`));
    })
  );
}


  openPdf(url: string) {
  console.log('PDF URL:', url);

  this.http.get('http://localhost:8080/organization/document', {
    params: { url },          // Pass URL as query param
    responseType: 'blob'      // Must be blob for PDF
  }).subscribe({
    next: (blob) => {
      const blobUrl = URL.createObjectURL(blob);
      window.open(blobUrl, '_blank');  // Open PDF in new tab
    },
    error: (err) => {
      console.error('Failed to open PDF', err.error);
    }
  });
}


}
