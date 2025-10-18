import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { VendorRequestDto } from '../dto/VendorRequestDto';
import { catchError, map, Observable, throwError } from 'rxjs';
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
  deleteVendor(vendorId:Number){}

  uploadFile(file: File): Observable<string> {
  return this.cloudinaryService.uploadFile(file).pipe(
    map((response: any) => response.secure_url),
    catchError((error: Error) => {
      console.log(error)
      console.error('File upload failed:', error);
      return throwError(() => new Error(`File upload failed: ${error.message}`));
    })
  );}
}
