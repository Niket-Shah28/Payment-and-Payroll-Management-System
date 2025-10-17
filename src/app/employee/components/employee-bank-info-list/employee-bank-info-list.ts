import { Component, OnInit } from '@angular/core';
import { AccountService } from '../../services/account-service';
import { EmployeeBankInfoDto } from '../../dto/employee-bank-info-dto';
import { EmployeeBankInfoUpdateDto } from '../../dto/employee-bank-info-update-dto';
import { Router } from '@angular/router';

@Component({
  selector: 'app-employee-bank-info-list',
  standalone: false,
  templateUrl: './employee-bank-info-list.html',
  styleUrl: './employee-bank-info-list.css'
})
export class EmployeeBankInfoList implements OnInit {

  bankInfos: EmployeeBankInfoDto[] = [];

  constructor(private accountService: AccountService, private router: Router) {}

  ngOnInit(): void {
    this.loadBankInfos();
  }

  loadBankInfos() {
    this.accountService.getAllBankInfo().subscribe({
      next: (info) => this.bankInfos = info,
      error: (err) => console.error('Error loading bank info', err)
    });
  }

  editBankInfo(bankInfo: EmployeeBankInfoDto) {
    // Always start from current values (so backend receives a full DTO)
    let updatedUan = bankInfo.uanNumber;
    let updatedPf = bankInfo.pfNumber;
    let updatedActive = bankInfo.isActive;

    // Ask user to optionally modify values
    const uanPrompt = prompt('Edit UAN Number:', updatedUan);
    if (uanPrompt !== null && uanPrompt.trim() !== '') updatedUan = uanPrompt.trim();

    const pfPrompt = prompt('Edit PF Number:', updatedPf);
    if (pfPrompt !== null && pfPrompt.trim() !== '') updatedPf = pfPrompt.trim();

    const activePrompt = prompt('Is Active? (true/false):', updatedActive ? 'true' : 'false');
    if (activePrompt !== null && (activePrompt.toLowerCase() === 'true' || activePrompt.toLowerCase() === 'false')) {
      updatedActive = activePrompt.toLowerCase() === 'true';
    }

    // Construct a *complete* DTO
    const updateDto: EmployeeBankInfoUpdateDto = {
      uanNumber: updatedUan,
      pfNumber: updatedPf,
      isActive: updatedActive
    };

    // Make the PATCH request
    this.accountService.updateBankInfo(bankInfo.employeeBankInfoId!, updateDto)
      .subscribe({
        next: (updated) => {
          alert('✅ Bank info updated successfully!');
          Object.assign(bankInfo, updated);
        },
        error: (err) => {
          console.error('❌ Error updating bank info:', err);
          alert('❌ Failed to update bank info');
        }
      });
  }

  // viewBankInfo(bankInfoId: number) {
  //   this.router.navigate(['/employee/dashboard/employee-bank-info-list', bankInfoId]);
  // }
}
