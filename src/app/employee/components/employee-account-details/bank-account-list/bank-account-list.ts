import { Component } from '@angular/core';
import { BankAccountDetailsDto } from '../../../dto/bank-account-details-dto';
import { BankAccountDetailsUpdateDto } from '../../../dto/bank-account-details-update-dto';
import { AccountService } from '../../../services/account-service';
import { Router } from '@angular/router';
import { AccountType } from '../../../dto/account-type';
 

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
    private router: Router 
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
  const updateDto: Partial<BankAccountDetailsUpdateDto> = {};

  // BANK NAME
  const newBankName = prompt('Edit Bank Name:', account.bankName);
  if (newBankName !== null && newBankName.trim() !== '') {
    updateDto.bankName = newBankName.trim();
  }

  // IFSC
  const newIfsc = prompt('Edit IFSC Code:', account.ifscCode);
  if (newIfsc !== null && newIfsc.trim() !== '') {
    updateDto.ifscCode = newIfsc.trim();
  }

  // ACCOUNT TYPE (enum)
  const newAccountType = prompt('Edit Account Type (SAVINGS/CURRENT):', account.accountType);
  if (newAccountType !== null && newAccountType.trim() !== '') {
    const type = newAccountType.toUpperCase();
    if (type === 'SAVINGS' || type === 'CURRENT') {
      updateDto.accountType = type as AccountType;
    } else {
      alert('Invalid account type! Edit cancelled.');
      return;
    }
  }

  // HOLDER NAME
  const newHolder = prompt('Edit Holder Name:', account.accountHolderName);
  if (newHolder !== null && newHolder.trim() !== '') {
    updateDto.accountHolderName = newHolder.trim();
  }

  // IS ACTIVE (boolean)
  const newActive = prompt('Is Active? (true/false):', account.isActive ? 'true' : 'false');
  if (newActive !== null && (newActive.toLowerCase() === 'true' || newActive.toLowerCase() === 'false')) {
    updateDto.isActive = newActive.toLowerCase() === 'true';
  }

  // IF NO CHANGES, do nothing
  if (Object.keys(updateDto).length === 0) {
    alert('No changes made.');
    return;
  }

  // CALL BACKEND
  this.accountService.updateAccount(account.accountId!, updateDto as BankAccountDetailsUpdateDto).subscribe({
    next: (updated) => {
      alert('Account updated successfully!');
      Object.assign(account, updated); // reflect changes in UI
    },
    error: (err) => {
      console.error('Error updating account', err);
      alert('Failed to update account');
    }
  });
}







//  viewAccount(accountId: number) {
//   alert(`View details for account ID: ${accountId}`);
// }

  goToEmployeeBankInfo() {
    this.router.navigate(['/employee/dashboard/employee-bank-info-list']);
  }

}
