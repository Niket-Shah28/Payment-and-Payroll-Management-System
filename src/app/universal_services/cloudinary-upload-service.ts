import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, switchMap } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CloudinaryUploadService {

  constructor(private http: HttpClient) {}

  uploadFile(file: File): Observable<string> {
  return new Observable<string>((observer) => {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('upload_preset', environment.CLOUDINARY_PRESET_ID);
    //formData.append('resource_type', 'raw'); // for PDF, CSV, etc.

    fetch(`https://api.cloudinary.com/v1_1/${environment.CLOUD_NAME}/raw/upload`, {
      method: 'POST',
      body: formData,
    })
      .then(async (res) => {
        const data = await res.json();
        if (data.secure_url) {
          console.log('Uploaded:', data);
          observer.next(data.secure_url);
          observer.complete();
        } else {
          throw new Error(data.error?.message || 'Upload failed');
        }
      })
      .catch((err) => {
        console.error('Cloudinary upload failed:', err);
        observer.error(err);
      });
  });
}
}
