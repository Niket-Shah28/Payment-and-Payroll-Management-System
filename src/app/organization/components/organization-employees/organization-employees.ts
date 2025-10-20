import { Component, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Employee } from '../../dto/Employee';
import { FormControl } from '@angular/forms';
import { EmployeeService } from '../../service/employee-service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { debounceTime, distinctUntilChanged } from 'rxjs';
import { EmployeePageResponse } from '../../dto/EmployeePageResponse';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';

@Component({
  selector: 'app-organization-employees',
  standalone: false,
  templateUrl: './organization-employees.html',
  styleUrl: './organization-employees.css'
})
export class OrganizationEmployees {
  displayedColumns: string[] = ['serialNumber', 'employeeId', 'name', 'role', 'department', 'businessUnit', 'actions'];
  dataSource: MatTableDataSource<Employee>;

  PAGE_SIZE_OPTIONS = [5, 10, 25, 50];
  DEFAULT_PAGE_SIZE = 10;
  
  searchControl = new FormControl('', { nonNullable: true });
  isLoading = false;
  isUploading = false;
  selectedFileName = '';
  isSearching = false;

  // Pagination variables
  pageIndex = 0;
  pageSize = this.DEFAULT_PAGE_SIZE;
  pageSizeOptions = this.PAGE_SIZE_OPTIONS;
  totalElements = 0;

  totalPages = 0;

  // Store all loaded data for client-side operations (Mocking DB)
  allEmployees: Employee[] = []; // Initialized to empty array as requested
  filteredEmployees: Employee[] = []; // Current filtered set

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  // Mocked SnackBar implementation
  private mockSnackBar: { open: (message: string, action: string, config: any) => void } = {
    open: (message, action, config) => console.log(`[SnackBar Mock - ${config.panelClass?.[0].split('-')[1] || 'info'}] ${message}`)
  };

  constructor(private employeeService: EmployeeService) {
    this.dataSource = new MatTableDataSource<Employee>([]);
  }

  ngOnInit(): void {
    this.loadEmployees();
  }

  ngAfterViewInit(): void {
    if (this.paginator) {
      this.dataSource.paginator = this.paginator;
    }
    if (this.sort) {
        this.dataSource.sort = this.sort;
    }
  }

  private loadEmployees(): void {
    this.isLoading = true;
    const searchTerm = this.searchControl.value.toLowerCase().trim();
    
    // Simulate API call delay
    this.employeeService.getEmployees(this.pageIndex, this.pageSize, '').subscribe({
      next: (response: EmployeePageResponse) => {
        this.allEmployees = response.content;
        this.filteredEmployees = response.content;
        this.dataSource.data = response.content;
        this.totalElements = response.totalElements;
        this.totalPages = response.totalPages;
        this.pageIndex = response.currentPage;
        this.pageSize = response.pageSize;
        this.isLoading = false;

        if (this.dataSource.data.length > 0) {
          this.showSnackBar(`Loaded ${response.totalElements} employees`, 'success');
        }
      },
      error: (error) => {
        this.showSnackBar('Failed to load employees: ' + error.message, 'error');
        this.isLoading = false;
      }
    });
  }

  /**
   * Triggers a new search operation.
   */
  onSearchClick(): void {
    const searchTerm = this.searchControl.value;

    if (!searchTerm.trim() && this.allEmployees.length > 0) {
      this.onResetSearch();
      return;
    }

    // Always reset to the first page on a new search term
    this.pageIndex = 0; 
    this.loadEmployees();
  }

  /**
   * Clears the search field and reloads the full employee list.
   */
  onResetSearch(): void {
    this.searchControl.setValue('');
    this.pageIndex = 0;
    this.loadEmployees();
    this.showSnackBar('Search reset. Showing all employees.', 'info');
  }

  /**
   * Handles pagination changes and reloads the data.
   */
  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadEmployees(); 
  }

  /**
   * Handles file selection for bulk upload.
   */
  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0];

    if (!file) {
      return;
    }

    if (file.type !== 'text/csv' && !file.name.endsWith('.csv')) {
      this.showSnackBar('Only CSV files are permitted', 'error');
      input.value = '';
      return;
    }

    this.selectedFileName = file.name;
    this.isUploading = true;

    // Simulate upload process
    setTimeout(() => {
      // In a real app, you would parse the CSV here and push data to allEmployees
      // For now, we mock adding 10 employees to show a change.
      const mockAddedEmployees: Employee[] = Array.from({ length: 10 }, (_, i) => ({
        id: `m${this.allEmployees.length + i + 1}`,
        employeeId: `MOCK-${100 + i}`,
        name: `Uploaded User ${i + 1}`,
        role: 'New Hire',
        department: 'HR',
        businessUnit: 'Support',
      }));

      this.allEmployees.push(...mockAddedEmployees);

      this.showSnackBar(`File processed. ${mockAddedEmployees.length} employees uploaded successfully (Mock).`, 'success');
      this.clearSelectedFile();
      this.isUploading = false;
      this.loadEmployees();
      input.value = '';
    }, 2000);
  }

  /**
   * Clears the selected file name from the UI.
   */
  clearSelectedFile(): void {
      this.selectedFileName = '';
  }

  /**
   * Generates and downloads a CSV template for bulk upload.
   */
  downloadBulkTemplate(): void {
    const headers = ['employeeId', 'name', 'role', 'department', 'businessUnit'];
    const sampleRow = ['EMP001', 'John Doe', 'Senior Developer', 'Engineering', 'Technology'];
    
    let csvContent = headers.join(',') + '\n';
    csvContent += sampleRow.join(',') + '\n';

    this.downloadCSV(csvContent, 'employee_bulk_upload_template.csv');
    this.showSnackBar('Template downloaded successfully', 'success');
  }

  /**
   * Simulates viewing a full employee profile.
   */
  viewFullProfile(employee: Employee): void {
    console.log('View profile for:', employee);
    this.showSnackBar(`Profile view for ${employee.name} - Coming soon`, 'info');
  }

  /**
   * Removes an employee from the mock list.
   */
  removeEmployee(employee: Employee): void {
    // NOTE: Using console.log instead of window.confirm in compliance with guidelines.
    console.log(`[Action] Attempting to remove employee ${employee.name}`);

    this.isLoading = true;
    
    // Simulate API call delay
    setTimeout(() => {
        // Mock successful removal
        this.allEmployees = this.allEmployees.filter(e => e.employeeId !== employee.employeeId);
        this.showSnackBar(`Employee ${employee.employeeId} removed successfully (Mock)`, 'success');
        this.loadEmployees(); // Reload data to update table
    }, 800);
  }

  /**
   * Helper function to download a CSV blob.
   */
  private downloadCSV(content: string, filename: string): void {
    const blob = new Blob([content], { type: 'text/csv;charset=utf-8;' });
    this.downloadFile(blob, filename);
  }
  
  /**
   * Core function to create and click a temporary download link.
   */
  private downloadFile(blob: Blob, filename: string): void {
    const link = document.createElement('a');
    const url = URL.createObjectURL(blob);
    link.setAttribute('href', url);
    link.setAttribute('download', filename);
    link.style.visibility = 'hidden';
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    URL.revokeObjectURL(url);
  }

  /**
   * Mock implementation of MatSnackBar.
   */
  private showSnackBar(message: string, type: 'success' | 'error' | 'info'): void {
    this.mockSnackBar.open(message, 'Close', {
      duration: 5000,
      horizontalPosition: 'end',
      verticalPosition: 'top',
      panelClass: type === 'success' ? ['snackbar-success'] : type === 'error' ? ['snackbar-error'] : ['snackbar-info']
    });
  }

  

  /**
   * Calculates the serial number for the current page index.
   */
  getSerialNumber(index: number): number {
    return this.pageIndex * this.pageSize + index + 1;
  }
}
