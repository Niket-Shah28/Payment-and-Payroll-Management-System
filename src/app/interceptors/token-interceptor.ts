import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginService } from '../auth/service/login-service';
import { Observable } from 'rxjs';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  private readonly EXCLUDED_URL = 'api.cloudinary.com';

  constructor(private authToken: LoginService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    if (req.url.includes(this.EXCLUDED_URL)) {
      // If it's the Cloudinary upload, skip the token logic entirely.
      console.log(`Bypassing token for external upload: ${req.url}`);
      return next.handle(req);
    }
    
    const token = this.authToken.getToken();
    
    if (token) {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const isExpired = Date.now() >= payload.exp * 1000;
      if(isExpired){
        localStorage.removeItem('accessToken');
        return next.handle(req);
      }
      const cloned = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
      return next.handle(cloned);
    }
    return next.handle(req);
  }
};
