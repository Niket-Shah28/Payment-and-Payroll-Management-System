import { Component, computed, inject, signal } from '@angular/core';
import { FormBuilder, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { PaymentRecipientType } from '../../dto/PaymentRecipientType';
import { Month } from '../../dto/Month';
import { PaymentMode } from '../../dto/PaymentMode';
import { PayoutFormValue } from '../../dto/PayoutFormValue';
import { map } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-organization-payout-component',
  standalone: false,
  templateUrl: './organization-payout-component.html',
  styleUrl: './organization-payout-component.css'
})
export class OrganizationPayoutComponent {
  payoutForm!: FormGroup;
  uploadedFileUrl: string | null = null;

  recipientTypes = ['EMPLOYEE', 'VENDOR'];
  paymentModes = ['BANK_TRANSFER', 'CHEQUE'];
  months = ['January','February','March','April','May','June','July','August','September','October','November','December'];
  currentYear = new Date().getFullYear();

  constructor(private fb: FormBuilder, private http: HttpClient) {}

  ngOnInit() {
    this.payoutForm = this.fb.group({
      paymentRecipientType: [null, Validators.required],
      singlePayment: [true, Validators.required],
      amount: [null],
      paymentMode: [null],
      scheduledTime: [null],
      recipientAccountNumber: [null],
      recipientBankName: [null],
      recipientIfscCode: [null],
      recipientAccountHolderName: [null],
      paymentFileUrl: [null],
      month: [null],
      year: [null],
    });

    this.setupDynamicValidation();
  }

  setupDynamicValidation() {
    this.payoutForm.get('singlePayment')!.valueChanges.subscribe(() => this.updateValidators());
    this.updateValidators();
  }

  updateValidators() {
    const isSingle = this.payoutForm.get('singlePayment')!.value;
    const controls = this.payoutForm.controls;

    ['amount','recipientAccountNumber','recipientBankName','recipientIfscCode','recipientAccountHolderName']
      .forEach(f => controls[f].setValidators(isSingle ? [Validators.required] : null));
    controls['amount'].updateValueAndValidity();

    controls['paymentFileUrl'].setValidators(!isSingle ? [Validators.required] : null);
    controls['paymentFileUrl'].updateValueAndValidity();

    ['paymentMode','scheduledTime'].forEach(f => {
      controls[f].setValidators([Validators.required]);
      controls[f].updateValueAndValidity();
    });
  }

  isSinglePayment(): boolean {
    return this.payoutForm.get('singlePayment')!.value;
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (!file) return;

    // const formData = new FormData();
    // formData.append('file', file);
    // formData.append('upload_preset', environment.CLOUDINARY_UPLOAD_PRESET);

    // const url = `https://api.cloudinary.com/v1_1/${environment.CLOUDINARY_CLOUD_NAME}/raw/upload`;

    // this.http.post<any>(url, formData).subscribe({
    //   next: (res) => {
    //     this.uploadedFileUrl = res.secure_url;
    //     this.payoutForm.get('paymentFileUrl')!.setValue(res.secure_url);
    //   },
    //   error: (err) => { alert('File upload failed!'); }
    // });
  }

  onSubmit() {
    if (this.payoutForm.valid) {
      console.log('Submitted:', this.payoutForm.value);
      alert(this.isSinglePayment() ? 'Single Payment Submitted' : 'Bulk Payment Submitted');
    } else {
      this.payoutForm.markAllAsTouched();
    }
  }

  downloadTemplate() { console.log('Download Template clicked'); }
  downloadData() { console.log('Download Data clicked'); }
}
