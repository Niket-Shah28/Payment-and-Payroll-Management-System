import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProfileResponseDto } from '../dto/profile-response-dto';
import { ProfileRequestDto } from '../dto/profile-request-dto';
import { EmployeeAddressResponseDto } from '../dto/employee-address-response-dto';
import { EmployeeAddressRequestDto } from '../dto/employee-address-request-dto';
import { EmployeeContactDetailsResponseDto } from '../dto/employee-contact-details-response-dto';
import { EmployeeContactDetailsRequestDto } from '../dto/employee-contact-details-request-dto';
import { LoginService } from '../../auth/service/login-service';

@Injectable({
  providedIn: 'root'
})
export class EmployeeProfileService {

  private baseUrl = 'http://localhost:8080/employee';

  constructor(private http: HttpClient, private loginService: LoginService) { }

  
 private getAuthHeaders() {
    const token = this.loginService.getToken();
    return { headers: new HttpHeaders({ 'Authorization': `Bearer ${token}` }) };
  }

  getProfile(): Observable<ProfileResponseDto> {
    return this.http.get<ProfileResponseDto>(`${this.baseUrl}/profile`, this.getAuthHeaders());
  }

  updateProfile(patchDto: ProfileRequestDto, file?: File): Observable<ProfileResponseDto> {
    const formData = new FormData();
    formData.append('profileData', new Blob([JSON.stringify(patchDto)], { type: 'application/json' }));
    if (file) formData.append('profilePhoto', file);
    return this.http.patch<ProfileResponseDto>(`${this.baseUrl}/profile`, formData, this.getAuthHeaders());
  }

  getAddress(): Observable<EmployeeAddressResponseDto> {
    return this.http.get<EmployeeAddressResponseDto>(`${this.baseUrl}/address`, this.getAuthHeaders());
  }

  updateAddress(patchDto: EmployeeAddressRequestDto): Observable<EmployeeAddressResponseDto> {
    return this.http.patch<EmployeeAddressResponseDto>(`${this.baseUrl}/address`, patchDto, this.getAuthHeaders());
  }

  getContactDetails(): Observable<EmployeeContactDetailsResponseDto> {
    return this.http.get<EmployeeContactDetailsResponseDto>(`${this.baseUrl}/contactdetails`, this.getAuthHeaders());
  }

  updateContactDetails(patchDto: EmployeeContactDetailsRequestDto): Observable<EmployeeContactDetailsResponseDto> {
    return this.http.patch<EmployeeContactDetailsResponseDto>(`${this.baseUrl}/contactdetails`, patchDto, this.getAuthHeaders());
  }
}
