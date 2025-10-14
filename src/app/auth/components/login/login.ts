import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from '../../service/login-service';
import { LoginResponseDto } from '../../dto/LoginResponseDto';
import { UserRole } from '../../dto/UserRole';
import { Router } from '@angular/router';

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

  constructor(private fb: FormBuilder, private loginService:LoginService, private router:Router) {
    this.loginForm = this.fb.group({
      referenceId: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  onSubmit() {
    this.loginService.sendData(this.loginForm.value).subscribe({
      next:(val:LoginResponseDto)=>{
        console.log(val)
        this.loginService.saveToken(val);
        this.role = this.loginService.getRole();

        console.log(this.role)

        if(this.role === UserRole.ROLE_ORGANIZATION){
          this.router.navigate(['/organization/dashboard'])
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
