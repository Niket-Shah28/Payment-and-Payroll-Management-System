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
  allPaymentRequests: PaymentRequest[] = []; // Store all requests
  paymentRequests: PaymentRequest[] = []; // Filtered requests for current view
  pageNumber = 0;
  pageSize = 3;
  totalPages = 0;
  viewMode: 'single' | 'bulk' = 'single'; // Toggle between single and bulk
  successMessage: string = '';
  errorMessage: string = '';

  constructor(
    private paymentService: PaymentsService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadPaymentRequests();
  }

  // Toggle between single and bulk view
  setViewMode(mode: 'single' | 'bulk'): void {
    this.viewMode = mode;
    this.pageNumber = 0; // Reset to first page
    this.filterPaymentsByMode();
    this.calculateTotalPages();
  }

  /**
   * Load all payment requests from the backend
   */
  loadPaymentRequests(): void {
    this.paymentService.getPaymentRequests(0, 1000).subscribe({
      next: (response) => {
        // Store all requests
        this.allPaymentRequests = response.content || [];
        //this.allPaymentRequests.forEach(a=> console.log(a));
        // Filter based on current view mode
        this.filterPaymentsByMode();
        // Calculate total pages for current mode
        this.calculateTotalPages();
      },
      error: (error) => {
        console.error('Error fetching payment requests', error);
        this.errorMessage = 'Failed to load payment requests';
        setTimeout(() => (this.errorMessage = ''), 3000);
      }
    });
  }

  /**
   * Filter payments based on viewMode
   * Single payments: singlePayment === true
   * Bulk payments: singlePayment === false
   */
  filterPaymentsByMode(): void {
    if (this.viewMode === 'single') {
      // Filter for single payments (singlePayment = true)
      this.paymentRequests = this.allPaymentRequests.filter(
        (payment) => payment.singlePayment === true
      );
    } else {
      // Filter for bulk payments (singlePayment = false)
      this.paymentRequests = this.allPaymentRequests.filter(
        (payment) => payment.singlePayment === false
      );
    }

    // Apply pagination
    this.applyPagination();
  }

  /**
   * Apply pagination to filtered requests
   */
  applyPagination(): void {
    const startIndex = this.pageNumber * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    this.paymentRequests = this.paymentRequests.slice(startIndex, endIndex);
  }

  /**
   * Calculate total pages based on filtered data
   */
  calculateTotalPages(): void {
    const filteredRequests = this.viewMode === 'single'
      ? this.allPaymentRequests.filter((p) => p.singlePayment === true)
      : this.allPaymentRequests.filter((p) => p.singlePayment === false);

    this.totalPages = Math.ceil(filteredRequests.length / this.pageSize);
  }

  /**
   * Update payment status (Approve/Reject)
   */
  updateStatus(paymentRequestId: number, status: Status): void {
    console.log('Updating status to', status);

    this.paymentService.updatePaymentStatus(paymentRequestId, status).subscribe({
      next: (res) => {
        console.log('✅ API success response:', res);
        //this.successMessage = `Payment ${status.toLowerCase()} successfully`;
        this.errorMessage = '';
        this.loadPaymentRequests();
        setTimeout(() => (this.successMessage = ''), 3000);
      },
      error: (error) => {
        // Handle 200/204 responses that Angular might interpret as errors
        if (error.status === 200 || error.status === 204) {
          console.warn('⚠️ Empty success response treated as success:', error);
          //this.successMessage = `Payment ${status.toLowerCase()} successfully`;
          this.errorMessage = '';
          this.loadPaymentRequests();
          setTimeout(() => (this.successMessage = ''), 3000);
          return;
        }

        console.error('❌ Actual error updating payment status:', error);
        this.errorMessage = 'Error updating payment status';
        setTimeout(() => (this.errorMessage = ''), 3000);
      }
    });
  }

  /**
   * Go to next page
   */
  nextPage(): void {
    if (this.pageNumber + 1 < this.totalPages) {
      this.pageNumber++;
      this.filterPaymentsByMode();
    }
  }

  /**
   * Go to previous page
   */
  prevPage(): void {
    if (this.pageNumber > 0) {
      this.pageNumber--;
      this.filterPaymentsByMode();
    }
  }
}