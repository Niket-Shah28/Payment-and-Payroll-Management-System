import { Component, computed, inject, signal } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, ValidatorFn, Validators } from '@angular/forms';
import { PaymentRecipientType } from '../../dto/PaymentRecipientType';
import { Month } from '../../dto/Month';
import { PaymentMode } from '../../dto/PaymentMode';
import { PayoutFormValue } from '../../dto/PayoutFormValue';
import { catchError, map, tap, throwError } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { OrganizationPayoutService } from '../../service/organization-payout-service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CloudinaryUploadService } from '../../../universal_services/cloudinary-upload-service';

@Component({
  selector: 'app-organization-payout-component',
  standalone: false,
  templateUrl: './organization-payout-component.html',
  styleUrl: './organization-payout-component.css'
})
export class OrganizationPayoutComponent {
  payoutForm: FormGroup;
  isSinglePayment = true;
  isSubmitting = false;
  isFileUploading = false;
  selectedFileName = '';
  file: File | null = null;

  // Enums for template
  PaymentRecipientType = PaymentRecipientType;
  PaymentMode = PaymentMode;
  Month = Month;

  recipientTypeOptions = Object.values(PaymentRecipientType);
  paymentModeOptions = Object.values(PaymentMode);
  monthOptions = Object.values(Month);
  currentYear = new Date().getFullYear();
  yearOptions = Array.from({ length: 5 }, (_, i) => this.currentYear + i);

  constructor(
    private fb: FormBuilder,
    private payoutService: OrganizationPayoutService,
    private snackBar: MatSnackBar,
    private cloudinaryService: CloudinaryUploadService
  ) {
    this.payoutForm = this.initializeForm();
  }

  ngOnInit(): void {
    this.setupFormValueChanges();
  }

  onRecipientTypeChange(): void {
    const recipientType = this.payoutForm.get('paymentRecipientType')?.value;
    
    // If employee is selected and bulk payment is active, switch to single
    if (recipientType === PaymentRecipientType.EMPLOYEE && !this.isSinglePayment) {
      this.payoutForm.get('singlePayment')?.setValue(true);
      this.showSnackBar('Bulk payment is not available for employees. Switched to single payment.', 'error');
    }
  }

  private initializeForm(): FormGroup {
    return this.fb.group({
      paymentRecipientType: [null, Validators.required],
      paymentMode: [null, Validators.required],
      singlePayment: [true, Validators.required],
      // Single Payment Fields
      amount: [null],
      recipientAccountHolderName: [null],
      recipientAccountNumber: [null],
      recipientBankName: [null],
      recipientIfscCode: [null],
      // Bulk Payment Fields
      paymentFileUrl: [null],
      month: [null],
      year: [null],
      scheduledTime: [null]
    });
  }

  private setupFormValueChanges(): void {
    this.payoutForm.get('singlePayment')?.valueChanges.subscribe(isSingle => {
      this.isSinglePayment = isSingle;
      this.updateFormValidators();
    });

    // Listen to recipient type changes
    this.payoutForm.get('paymentRecipientType')?.valueChanges.subscribe(() => {
      this.onRecipientTypeChange();
    });
  }

  private updateFormValidators(): void {
    const amountCtrl = this.payoutForm.get('amount');
    const accountHolderCtrl = this.payoutForm.get('recipientAccountHolderName');
    const accountNumberCtrl = this.payoutForm.get('recipientAccountNumber');
    const bankNameCtrl = this.payoutForm.get('recipientBankName');
    const ifscCtrl = this.payoutForm.get('recipientIfscCode');
    const fileUrlCtrl = this.payoutForm.get('paymentFileUrl');
    const monthCtrl = this.payoutForm.get('month');
    const yearCtrl = this.payoutForm.get('year');

    // Clear validators
    [amountCtrl, accountHolderCtrl, accountNumberCtrl, bankNameCtrl, ifscCtrl, fileUrlCtrl, monthCtrl, yearCtrl].forEach(ctrl => {
      ctrl?.clearAsyncValidators();
      ctrl?.clearValidators();
      ctrl?.updateValueAndValidity();
    });

    if (this.isSinglePayment) {
      amountCtrl?.setValidators([Validators.required, Validators.min(0.01)]);
      accountHolderCtrl?.setValidators([Validators.required, Validators.minLength(2)]);
      accountNumberCtrl?.setValidators([Validators.required, Validators.minLength(9)]);
      bankNameCtrl?.setValidators([Validators.required]);
      ifscCtrl?.setValidators([Validators.required, Validators.pattern(/^[A-Z]{4}0[A-Z0-9]{6}$/)]);
    } else {
      fileUrlCtrl?.setValidators([Validators.required]);
      monthCtrl?.setValidators([Validators.required]);
      yearCtrl?.setValidators([Validators.required]);
    }

    [amountCtrl, accountHolderCtrl, accountNumberCtrl, bankNameCtrl, ifscCtrl, fileUrlCtrl, monthCtrl, yearCtrl].forEach(ctrl => {
      ctrl?.updateValueAndValidity({ emitEvent: false });
    });
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0];

    if (!file) {
      return;
    }

    if (file.type !== 'text/csv') {
      this.showSnackBar('Only CSV files are permitted', 'error');
      input.value = '';
      return;
    }

    this.selectedFileName = file.name;
    this.file = file;
    this.payoutForm.get('paymentFileUrl')?.setValue(file.name);
  }

  downloadTemplate(): void {
    this.payoutService.downloadSalaryTemplate().subscribe((blob) => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'payroll_template.csv';
      a.click();
      URL.revokeObjectURL(url);
    });
  }

  downloadStoredData(): void {
    this.isFileUploading = true;
    this.payoutService.downloadStoredData().subscribe((blob) => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'payroll.csv';
      a.click();
      URL.revokeObjectURL(url);
    });

  }

  onSubmit(): void {
    if (!this.payoutForm.valid) {
      this.showSnackBar('Please fill all required fields correctly', 'error');
      return;
    }

    this.isSubmitting = true;
    const payload = this.buildPayload();

    if (this.file && !this.isSinglePayment) {
      // Upload file to Cloudinary first
      this.cloudinaryService.uploadFile(this.file).pipe(
        tap((response: any) => {
          payload.paymentFileUrl = response;
        }),
        catchError((error: Error) => {
          this.showSnackBar(`File upload failed: ${error.message}`, 'error');
          this.isSubmitting = false;
          throw error;
        })
      ).subscribe({
        next: () => {
          this.initiatePayment(payload);
        },
        error: () => {
          this.isSubmitting = false;
        }
      });
    } else {
      this.initiatePayment(payload);
    }
  }

  private initiatePayment(payload: PayoutFormValue): void {
    this.payoutService.addRequest(payload).subscribe({
      next: () => {
        this.showSnackBar(
          this.isSinglePayment ? 'Single payment initiated successfully' : 'Bulk payout initiated successfully',
          'success'
        );
        this.resetForm();
        this.isSubmitting = false;
      },
      error: (error) => {
        this.showSnackBar('Failed to initiate payout: ' + error.message, 'error');
        this.isSubmitting = false;
      }
    });
  }

  private buildPayload(): PayoutFormValue {
    const formValue = this.payoutForm.value;
    const payload = new PayoutFormValue();

    payload.paymentRecipientType = formValue.paymentRecipientType;
    payload.paymentMode = formValue.paymentMode;
    payload.singlePayment = formValue.singlePayment;

    if (this.isSinglePayment) {
      payload.amount = formValue.amount;
      payload.recipientAccountHolderName = formValue.recipientAccountHolderName;
      payload.recipientAccountNumber = formValue.recipientAccountNumber;
      payload.recipientBankName = formValue.recipientBankName;
      payload.recipientIfscCode = formValue.recipientIfscCode;
    } else {
      payload.paymentFileUrl = formValue.paymentFileUrl;
      payload.month = formValue.month;
      payload.year = formValue.year;
      payload.scheduledTime = formValue.scheduledTime ? new Date(formValue.scheduledTime) : null;
    }

    return payload;
  }

  onClear(): void {
    this.payoutForm.reset({
      singlePayment: true,
      paymentRecipientType: null,
      paymentMode: null
    });
    this.selectedFileName = '';
    this.file = null;
    this.isSinglePayment = true;
  }

  private showSnackBar(message: string, type: 'success' | 'error'): void {
    this.snackBar.open(message, 'Close', {
      duration: 5000,
      horizontalPosition: 'end',
      verticalPosition: 'top',
      panelClass: type === 'success' ? ['snackbar-success'] : ['snackbar-error']
    });
  }

  private resetForm(): void {
    this.payoutForm.reset({
      singlePayment: true,
      paymentRecipientType: null,
      paymentMode: null
    });
    this.selectedFileName = '';
    this.file = null;
    this.isSinglePayment = true;
  }
}
