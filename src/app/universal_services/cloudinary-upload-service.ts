import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CloudinaryUploadService {

  constructor(private http: HttpClient) {}

  uploadFile(file: File): Observable<any> {
    const url = `https://api.cloudinary.com/v1_1/${environment.CLOUD_NAME}/raw/upload`;
    const formData = new FormData();
    formData.append('file', file);
    formData.append('upload_preset', environment.CLOUDINARY_PRESET_ID);
    return this.http.post(url, formData);
  }
}
