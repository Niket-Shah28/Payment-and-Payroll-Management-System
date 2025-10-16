import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginRequestDto } from '../dto/LoginRequestDto';
import { LoginResponseDto } from '../dto/LoginResponseDto';
import { UserRole } from '../dto/UserRole';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http:HttpClient){}

  url="http://localhost:8080/auth/login";
  sendData(data:LoginRequestDto):Observable<LoginResponseDto>{
    return this.http.post<LoginResponseDto>(this.url, data);
  }

  saveToken(dto:LoginResponseDto){
    localStorage.setItem("accessToken", dto['accessToken']);
  }

  getToken(){
    return localStorage.getItem("accessToken");
    
  }

  getRole(){
    const accessToken = localStorage.getItem("accessToken");
    const payload = accessToken?.split(".")[1];
    console.log(accessToken);
    const decodedPayload = JSON.parse(atob(payload!));
    return decodedPayload['role'][0]['authority'] as UserRole;
  }



}
