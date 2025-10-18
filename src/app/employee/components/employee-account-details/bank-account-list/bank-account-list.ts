import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BankAccountDetailsDto } from '../../../dto/bank-account-details-dto';
import { BankAccountDetailsUpdateDto } from '../../../dto/bank-account-details-update-dto';
import { EmployeeBankInfoDto } from '../../../dto/employee-bank-info-dto';
import { EmployeeBankInfoUpdateDto } from '../../../dto/employee-bank-info-update-dto';
import { AccountService } from '../../../services/account-service';
import { AccountType } from '../../../dto/account-type';
 

@Component({
  selector: 'app-bank-account-list',
  standalone: false,
  templateUrl: './bank-account-list.html',
  styleUrl: './bank-account-list.css'
})
export class BankAccountList {

  accounts: BankAccountDetailsDto[] = [];
  bankInfos: EmployeeBankInfoDto[] = [];

  constructor(private accountService: AccountService, private router: Router) {}

  ngOnInit(): void {
    this.loadAccounts();
    this.loadBankInfos();
  }

  /** Load all bank accounts */
  loadAccounts() {
    this.accountService.getAllAccounts().subscribe({
      next: (accounts) => this.accounts = accounts,
      error: (err) => console.error('Error loading accounts', err)
    });
  }

  /** Load all employee bank info */
  loadBankInfos() {
    this.accountService.getAllBankInfo().subscribe({
      next: (info) => this.bankInfos = info,
      error: (err) => console.error('Error loading bank info', err)
    });
  }

  /** Edit bank account */
  editAccount(account: BankAccountDetailsDto) {
    const updateDto: Partial<BankAccountDetailsUpdateDto> = {};

    const newBankName = prompt('Edit Bank Name:', account.bankName);
    if (newBankName?.trim()) updateDto.bankName = newBankName.trim();

    const newIfsc = prompt('Edit IFSC Code:', account.ifscCode);
    if (newIfsc?.trim()) updateDto.ifscCode = newIfsc.trim();

    const newAccountType = prompt('Edit Account Type (SAVINGS/CURRENT):', account.accountType);
    if (newAccountType?.trim()) {
      const type = newAccountType.toUpperCase();
      if (type === 'SAVINGS' || type === 'CURRENT') updateDto.accountType = type as AccountType;
      else { alert('Invalid account type! Edit cancelled.'); return; }
    }

    const newHolder = prompt('Edit Holder Name:', account.accountHolderName);
    if (newHolder?.trim()) updateDto.accountHolderName = newHolder.trim();

    const newActive = prompt('Is Active? (true/false):', account.isActive ? 'true' : 'false');
    if (newActive?.toLowerCase() === 'true' || newActive?.toLowerCase() === 'false') {
      updateDto.isActive = newActive.toLowerCase() === 'true';
    }

    if (Object.keys(updateDto).length === 0) { alert('No changes made.'); return; }

    this.accountService.updateAccount(account.accountId!, updateDto as BankAccountDetailsUpdateDto)
      .subscribe({
        next: (updated) => {
          alert('Account updated successfully!');
          Object.assign(account, updated);
        },
        error: (err) => {
          console.error('Error updating account', err);
          alert('Failed to update account');
        }
      });
  }

  /** Edit employee bank info */
  editBankInfo(bankInfo: EmployeeBankInfoDto) {
    let updatedUan = bankInfo.uanNumber;
    let updatedPf = bankInfo.pfNumber;
    let updatedActive = bankInfo.isActive;

    const uanPrompt = prompt('Edit UAN Number:', updatedUan);
    if (uanPrompt?.trim()) updatedUan = uanPrompt.trim();

    const pfPrompt = prompt('Edit PF Number:', updatedPf);
    if (pfPrompt?.trim()) updatedPf = pfPrompt.trim();

    const activePrompt = prompt('Is Active? (true/false):', updatedActive ? 'true' : 'false');
    if (activePrompt?.toLowerCase() === 'true' || activePrompt?.toLowerCase() === 'false') {
      updatedActive = activePrompt.toLowerCase() === 'true';
    }

    const updateDto: EmployeeBankInfoUpdateDto = {
      uanNumber: updatedUan,
      pfNumber: updatedPf,
      isActive: updatedActive
    };


   if (updatedUan === bankInfo.uanNumber &&
      updatedPf === bankInfo.pfNumber &&
      updatedActive === bankInfo.isActive) {
    alert('No changes made.');
    return;
  }

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

  /** Optional navigation method */
  goToEmployeeBankInfo() {
    this.router.navigate(['/employee/dashboard/employee-bank-info-list']);
  }
}
