import { Component } from '@angular/core';
import { BankAccountDetailsDto } from '../../../dto/bank-account-details-dto';
import { BankAccountDetailsUpdateDto } from '../../../dto/bank-account-details-update-dto';
import { AccountService } from '../../../services/account-service';
import { Router } from '@angular/router';
 

@Component({
  selector: 'app-bank-account-list',
  standalone: false,
  templateUrl: './bank-account-list.html',
  styleUrl: './bank-account-list.css'
})
export class BankAccountList {

  accounts: BankAccountDetailsDto[] = [];
  
 constructor(
    private accountService: AccountService,
    private router: Router // ✅ Inject Router properly
  ) {}


  ngOnInit(): void {
    this.loadAccounts();
  }

  loadAccounts() {
    this.accountService.getAllAccounts().subscribe({
      next: (accounts) => (this.accounts = accounts),
      error: (err) => console.error('Error loading accounts', err),
    });
  }

editAccount(account: BankAccountDetailsDto) {
    const newBankName = prompt('Edit Bank Name:', account.bankName);
    if (!newBankName) return;

    const updateDto: BankAccountDetailsUpdateDto = {
      bankName: newBankName,
      accountNumber: account.accountNumber, 
      ifscCode: account.ifscCode,
      accountType: account.accountType,
      accountHolderName: account.accountHolderName,
      isActive: account.isActive,
    };

    this.accountService.updateAccount(account.accountId!, updateDto).subscribe({
      next: (updated) => {
        alert('Account updated successfully');
        account.bankName = updated.bankName;
      },
      error: (err) => {
        console.error('Error updating account', err);
        alert('Failed to update account');
      },
    });
  }

  viewAccount(accountId: number) {
    this.router.navigate(['/employee/dashboard/bank-account', accountId]);
  }

  goToEmployeeBankInfo() {
    this.router.navigate(['employee/dashboard/bank-info']);
  }

}
