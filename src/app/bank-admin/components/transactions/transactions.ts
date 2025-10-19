import { Component, OnInit } from '@angular/core';
import { TransactionsService } from '../../services/transaction-service'; 
import { TransactionDetailsDto } from '../../dto/transaction-details.model'; 
import { Page } from '../../dto/page.model'; 

@Component({
  selector: 'app-transactions',
  standalone: false,
  templateUrl: './transactions.html',
  styleUrl: './transactions.css'
})
export class Transactions {
transactions: TransactionDetailsDto[] = [];
  loading = false;
  error = '';

  // filters
  entityName = '';
  dateFromInput = ''; 
  dateToInput = '';

  // paging
  pageNumber = 0;
  pageSize = 10;
  totalPages = 0;
  totalElements = 0;

  // UI state
  showFilters = true;

  constructor(private txService: TransactionsService) {}

  ngOnInit(): void {
    this.loadTransactions();
  }

  private toIsoLocal(datetimeLocal: string | null): string | undefined {
    if (!datetimeLocal) return undefined;
    const d = new Date(datetimeLocal);
    if (isNaN(d.getTime())) return undefined;
    return d.toISOString();
  }

  loadTransactions(page: number = 0): void {
    this.loading = true;
    this.error = '';

    const fromIso = this.toIsoLocal(this.dateFromInput);
    const toIso = this.toIsoLocal(this.dateToInput);

    this.txService.getTransactions(this.entityName || undefined, fromIso, toIso, page, this.pageSize)
      .subscribe({
        next: (pageObj: Page<TransactionDetailsDto>) => {
          this.transactions = pageObj.content || [];
          this.pageNumber = pageObj.number ?? page;
          this.pageSize = pageObj.size ?? this.pageSize;
          this.totalPages = pageObj.totalPages ?? 0;
          this.totalElements = pageObj.totalElements ?? 0;
          this.loading = false;
        },
        error: (err) => {
          console.error(err);
          this.error = 'Failed to load transactions';
          this.loading = false;
        }
      });
  }

  applyFilters(): void {
    this.pageNumber = 0;
    this.loadTransactions(0);
  }

  clearFilters(): void {
    this.entityName = '';
    this.dateFromInput = '';
    this.dateToInput = '';
    this.applyFilters();
  }

  goToPage(page: number): void {
    if (page < 0 || page >= this.totalPages) return;
    this.pageNumber = page;
    this.loadTransactions(page);
  }

  formatDate(iso: string | undefined): string {
    if (!iso) return '-';
    const d = new Date(iso);
    return d.toLocaleString();
  }
}
