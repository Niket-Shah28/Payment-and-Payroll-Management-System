import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  constructor() {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // 1. Get the token from local storage (or wherever you store it after login)
    // NOTE: Replace 'jwtToken' with the actual key you use to store your token.
    const token = localStorage.getItem('jwtToken'); 
    
    // 2. Clone the request and add the Authorization header if a token exists
    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }

    // 3. Pass the cloned request to the next handler
    return next.handle(request);
  }
}
