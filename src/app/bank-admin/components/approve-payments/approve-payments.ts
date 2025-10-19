import { Component, OnInit } from '@angular/core';
import { PaymentRequest } from '../../dto/payment-request-dto';
import { Status } from '../../dto/status';
import { PaymentsService } from '../../services/payments-service'; 
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-approve-payments',
  standalone: false,
  templateUrl: './approve-payments.html',
  styleUrls: ['./approve-payments.css']
})
export class ApprovePayments implements OnInit {

  paymentRequests: PaymentRequest[] = [];
  pageNumber = 0;
  pageSize = 3;
  totalPages = 0;

  successMessage: string = '';
  errorMessage: string = '';

  // constructor(private paymentService: PaymentsService) { }

  ngOnInit(): void {
    this.loadPaymentRequests();
  }

  loadPaymentRequests(): void {
    this.paymentService.getPaymentRequests(this.pageNumber, this.pageSize).subscribe({
      next: (response) => {
        this.paymentRequests = response.content;
        this.totalPages = response.totalPages;
      },
      error: (error) => {
        console.error('Error fetching payment requests', error);
      }
    });
  }

  
constructor(private paymentService: PaymentsService,
            private cdr: ChangeDetectorRef) {}

updateStatus(paymentRequestId: number, status: Status): void {

  console.log('Updating status to', status);
 
  this.paymentService.updatePaymentStatus(paymentRequestId, status).subscribe({

    next: (res) => {

      console.log('✅ API success response:', res);
 
      // Treat 204/empty response as success too

      this.successMessage = `Payment ${status.toLowerCase()} successfully`;

      this.errorMessage = '';

      this.loadPaymentRequests();

      setTimeout(() => (this.successMessage = ''), 3000);

    },

    error: (error) => {

      // ⚙️ If backend returns 200/204 but Angular misinterprets it as an error

      if (error.status === 200 || error.status === 204) {

        console.warn('⚠️ Empty success response treated as success:', error);

        this.successMessage = `Payment ${status.toLowerCase()} successfully`;

        this.errorMessage = '';

        this.loadPaymentRequests();

        setTimeout(() => (this.successMessage = ''), 3000);

        return;

      }
 
      console.error('❌ Actual error updating payment status:', error);

      this.errorMessage = 'Error updating payment status';

      setTimeout(() => (this.errorMessage = ''), 3000);

    },

  });

}

 




  nextPage(): void {
    if (this.pageNumber + 1 < this.totalPages) {
      this.pageNumber++;
      this.loadPaymentRequests();
    }
  }

  prevPage(): void {
    if (this.pageNumber > 0) {
      this.pageNumber--;
      this.loadPaymentRequests();
    }
  }
}