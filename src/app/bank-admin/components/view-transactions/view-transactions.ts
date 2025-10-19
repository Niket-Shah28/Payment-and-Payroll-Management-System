import { Component, OnInit } from '@angular/core';
import { BankTransactionService } from '../../services/bank-transaction-service';
import { TransactionDetailsDto } from '../../dto/transaction-details-dto';

@Component({
  selector: 'app-view-transactions',
  standalone: false,
  templateUrl: './view-transactions.html',
  styleUrl: './view-transactions.css'
})
export class ViewTransactions implements OnInit{

   transactions: TransactionDetailsDto[] = [];
  filteredTransactions: TransactionDetailsDto[] = [];

  searchQuery: string = '';
  filterStatus: string = '';
  filterPaymentMode: string = '';
  filterPaymentType: string = '';
  dateFrom: string = '';
  dateTo: string = '';

  currentPage: number = 0;
  totalPages: number = 0;
  pageSize: number = 10;

  loading: boolean = false;
  error: string = '';

  constructor(private transactionService: BankTransactionService) {}

  ngOnInit() {
    this.loadTransactions();
  }

  loadTransactions() {
    this.loading = true;
    this.transactionService.getTransactions(
      this.searchQuery,
      this.dateFrom ? `${this.dateFrom}T00:00:00` : undefined,
      this.dateTo ? `${this.dateTo}T23:59:59` : undefined,
      this.currentPage,
      this.pageSize
    ).subscribe({
      next: (response) => {
        this.transactions = response.content || [];
        this.totalPages = response.totalPages;
        this.applyLocalFilters();
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to fetch transactions.';
        console.error(err);
        this.loading = false;
      }
    });
  }

  applyLocalFilters() {
    this.filteredTransactions = this.transactions.filter(t => {
      const matchesSearch =
        this.searchQuery === '' ||
        t.sourceAccountNumber.includes(this.searchQuery) ||
        t.destinationAccountNumber.includes(this.searchQuery) ||
        t.receiverHolderName.toLowerCase().includes(this.searchQuery.toLowerCase());

      const matchesStatus = this.filterStatus === '' || t.transactionStatus === this.filterStatus;
      const matchesMode = this.filterPaymentMode === '' || t.paymentMode === this.filterPaymentMode;
      const matchesType = this.filterPaymentType === '' || t.paymentType === this.filterPaymentType;

      return matchesSearch && matchesStatus && matchesMode && matchesType;
    });
  }

  onFilterChange() {
    this.applyLocalFilters();
  }

  onSearchChange() {
    this.applyLocalFilters();
  }

  nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadTransactions();
    }
  }

  prevPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadTransactions();
    }
  }

}
