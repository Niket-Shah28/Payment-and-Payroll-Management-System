import { Component, computed, signal } from '@angular/core';
import { BankAccountDto } from '../../dto/BankAccountDto';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BankAccountRequestDto } from '../../dto/BankAccountRequestDto';
import { OrganizationBankAccountService } from '../../service/organization-bank-account-service';
import { OrganizationBusinessUnitService } from '../../service/organization-business-unit-service';
import { AccountType } from '../../dto/AccountType';
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-bank-account',
  standalone: false,
  templateUrl: './bank-account.html',
  styleUrl: './bank-account.css'
})
export class BankAccount {
  bankAccount = signal<BankAccountDto | null>(null);
  isEditing = signal(false);
  showAddForm = signal(false);
  errorMessage = signal<string | null>(null);
  isLoading = signal(false);
  showDepositInput = signal(false);

  private deleteConfirmMsg = 'Are you sure you want to delete this bank account?';
  isConfirmationMessage = computed(() => this.errorMessage() === this.deleteConfirmMsg);

  addForm: FormGroup;
  editForm: FormGroup;
  depositForm: FormGroup;

  isFormDirty = computed(() => this.editForm.dirty);

  fields: { key: keyof BankAccountDto, label: string, type: string }[] = [
    { key: 'accountHolderName', label: 'Holder Name', type: 'text' },
    { key: 'accountNumber', label: 'Account No.', type: 'text' },
    { key: 'ifscCode', label: 'IFSC Code', type: 'text' },
    { key: 'accountType', label: 'Account Type', type: 'select' },
    { key: 'balance', label: 'Balance', type: 'number' }
  ];

  constructor(
    private fb: FormBuilder, 
    private snackBar: MatSnackBar, 
    private accountService: OrganizationBankAccountService
  ) {
      this.addForm = this.fb.group({
          accountHolderName: ['', Validators.required],
          accountNumber: ['', [Validators.required, Validators.minLength(9), Validators.pattern('^[0-9]*$')]],
          ifscCode: ['', [Validators.required, Validators.pattern('^[A-Z]{4}0[A-Z0-9]{6}$')]],
          accountType: ['', Validators.required],
          balance: [0, [Validators.required, Validators.min(0)]]
      });

      this.editForm = this.fb.group({
        accountHolderName: ['', Validators.required],
        accountNumber: ['', [Validators.required, Validators.minLength(9), Validators.pattern('^[0-9]*$')]],
        ifscCode: ['', [Validators.required, Validators.pattern('^[A-Z]{4}0[A-Z0-9]{6}$')]],
        accountType: ['', Validators.required],
        balance: [0, [Validators.required, Validators.min(0)]]
      });

      this.depositForm = this.fb.group({
        amount: [null, [Validators.required, Validators.min(0.01)]]
      });
  }

  ngOnInit() {
      this.loadAccount();
  }

  loadAccount() {
    this.isLoading.set(true);
    this.errorMessage.set(null);
    this.accountService.getAccount().subscribe({
      next: (val: BankAccountDto) => {
        this.bankAccount.set(val);
        this.showAddForm.set(false);
        this.isLoading.set(false);
      },
      error: (err) => {
        this.bankAccount.set(null);
        this.showAddForm.set(true);
        this.isLoading.set(false);
      }
    });
  }

  toggleAddForm(): void {
    this.showAddForm.update(val => !val);
    this.addForm.reset({ balance: 0 });
  }

  addAccount(): void {
    if (this.addForm.invalid || this.isLoading()) return;

    this.isLoading.set(true);
    this.errorMessage.set(null);
    
    const newAccountData: BankAccountRequestDto = this.addForm.value;

    this.accountService.addAccount(newAccountData).subscribe({
      next: () => {
          this.loadAccount();
          this.snackBar.open('Account added successfully!', 'Close', { duration: 3000 });
          this.isLoading.set(false);
      },
      error: (err) => {
          this.errorMessage.set(err.message || 'Failed to add account.');
      },
      complete: () => {
          this.isLoading.set(false);
      }
    });
  }

  startEdit(): void {
    const currentAccount = this.bankAccount();
    if (currentAccount) {
      this.editForm.patchValue(currentAccount);
      this.editForm.markAsPristine();
      this.isEditing.set(true);
    }
  }

  cancelEdit(): void {
    this.isEditing.set(false);
    this.editForm.reset(this.bankAccount()); // Reset form back to original state
  }

  saveEdit(): void {
    if (this.editForm.invalid || !this.isFormDirty() || this.isLoading()) return;

    this.isLoading.set(true);
    this.errorMessage.set(null);

    const currentAccountId = this.bankAccount()!.accountId;

    const updatedDto = new BankAccountDto(
      currentAccountId,
      this.editForm.value.accountNumber,
      this.editForm.value.accountHolderName,
      this.editForm.value.ifscCode,
      this.editForm.value.accountType as AccountType,
      this.editForm.value.balance
    );
    console.log(this.editForm.value.balance)
    console.log(updatedDto)
    this.accountService.updateAccount(updatedDto).subscribe({
      next: () => {
          
        this.bankAccount.set(updatedDto);
        this.isEditing.set(false);
        this.isLoading.set(false);
        this.snackBar.open('Account updated successfully!', 'Close', { duration: 3000 });
      },
      error: (err) => {
        this.errorMessage.set(err.message || 'Failed to update account.');
      },
      complete: () => {
        this.isLoading.set(false);
      }
    });
  }

  confirmDelete(): void {
    this.errorMessage.set(this.deleteConfirmMsg);
  }

  deleteAccount(accountId: Number): void {
    this.isLoading.set(true);
    this.errorMessage.set(null);

    this.accountService.deleteAccount(accountId).subscribe({
      next: () => {
        this.bankAccount.set(null);
        this.showAddForm.set(true);
        this.isLoading.set(false);
        this.snackBar.open('Account deleted successfully!', 'Close', { duration: 3000 });
      },
      error: (err) => {
        this.errorMessage.set(err.message || 'Failed to delete account.');
      },
      complete: () => {
        this.isLoading.set(false);
      }
    });
  }

  startDeposit(): void {
      this.isEditing.set(false); // Ensure edit mode is off
      this.showDepositInput.set(true);
      this.depositForm.reset();
      // Give time for the form to appear before marking as touched
      setTimeout(() => this.depositForm.markAllAsTouched(), 100);
    }

    cancelDeposit(): void {
        this.showDepositInput.set(false);
        this.depositForm.reset();
    }

    handleDeposit(): void {
        if (this.depositForm.invalid || this.isLoading()) return;

        this.isLoading.set(true);
        this.errorMessage.set(null);

        const amountToDeposit: number = this.depositForm.value.amount;
        const currentAccountId = this.bankAccount()!.accountId;

        this.accountService.depositAmount(currentAccountId, amountToDeposit).subscribe({
            next: () => {
              this.showDepositInput.set(false);
              this.depositForm.reset();
              this.snackBar.open(`Successfully deposited ${this.formatCurrency(amountToDeposit)}`, 'Close', { duration: 3000 });
              this.loadAccount();  
            },
            error: (err) => {
                this.errorMessage.set(err.message || 'Deposit failed.');
            },
            complete: () => {
                this.isLoading.set(false);
            }
        });
    }

    // Helper for formatting currency in TS for snackbar message
    private formatCurrency(value: number): string {
        return new CurrencyPipe('en-US').transform(value, 'INR', 'symbol', '1.2-2') || 'INR 0.00';
    }

  clearError(confirmed: boolean): void {
    const accountId = this.bankAccount()?.accountId;
    
    if (this.isConfirmationMessage()) {
      this.errorMessage.set(null);
      if (confirmed && accountId) {
        this.deleteAccount(accountId);
      }
    } else {
      this.errorMessage.set(null);
    }
  }
}
