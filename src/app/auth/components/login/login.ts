import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from '../../service/login-service';
import { LoginResponseDto } from '../../dto/LoginResponseDto';
import { UserRole } from '../../dto/UserRole';
import { Router } from '@angular/router';
import { environment } from '../../../../environments/environment';
import { LoginRequestDto } from '../../dto/LoginRequestDto';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
  loginForm !: FormGroup;
  showPassword = false;
  role !: UserRole;
  scriptLoaded = false;
  captchaResponse: string = "";
  capatchaSiteKey:string="";
  

  constructor(private fb: FormBuilder, private loginService:LoginService, private router:Router) {
    this.loginForm = this.fb.group({
      referenceId: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.loadRecaptchaScript();
    this.capatchaSiteKey=environment.CAPATCHA_SITE_KEY;
    (window as any).onCaptchaResolved = (response: string) => {
      this.captchaResponse = response;
    };
  }

  ngOnDestroy() {
    this.removeRecaptchaScript();
  }

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  loadRecaptchaScript() {
    if (this.scriptLoaded) return;
    const script = document.createElement('script');
    script.src = 'https://www.google.com/recaptcha/api.js';
    script.async = true;
    script.defer = true;
    document.body.appendChild(script);
    this.scriptLoaded = true;
  }

  removeRecaptchaScript() {
    const existing = document.querySelector('script[src*="recaptcha/api.js"]');
    if (existing) existing.remove();
  }

  onSubmit() {
    const payload: LoginRequestDto = {
      referenceId: this.loginForm.value.referenceId,
      password: this.loginForm.value.password,
      capatchaResponse: this.captchaResponse
    };
    this.loginService.sendData(payload).subscribe({
      next:(val:LoginResponseDto)=>{
        this.loginService.saveToken(val);
        this.role = this.loginService.getRole();
        if(this.role === UserRole.ROLE_ORGANIZATION){
          this.router.navigate(['/organization/dashboard/home'])
        }
        else if(this.role === UserRole.ROLE_EMPLOYEE){
          this.router.navigate(['/employee/dashboard'])
        }
        else{
          this.router.navigate(['/bank-admin/dashboard'])
        }
      },
      error:(er)=>{
        console.log("error")
        alert(er.error?.message);
      }
    })
  }
}
