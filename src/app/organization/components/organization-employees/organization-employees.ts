import { ChangeDetectorRef, Component, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Employee } from '../../dto/Employee';
import { FormControl } from '@angular/forms';
import { EmployeeService } from '../../service/employee-service';
import { EmployeePageResponse } from '../../dto/EmployeePageResponse';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatDialog } from '@angular/material/dialog';
import { AddEmployeeDialogComponent } from '../add-employee-dialog-component/add-employee-dialog-component';

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
  hasNextPage = false;

  pageIndex = 0;
  pageSize = this.DEFAULT_PAGE_SIZE;
  pageSizeOptions = this.PAGE_SIZE_OPTIONS;
  totalElements = 0;

  totalPages = 0;

  allEmployees: Employee[] = [];
  filteredEmployees: Employee[] = [];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  private mockSnackBar: { open: (message: string, action: string, config: any) => void } = {
    open: (message, action, config) => console.log(`[SnackBar Mock - ${config.panelClass?.[0].split('-')[1] || 'info'}] ${message}`)
  };

  constructor(
    private employeeService: EmployeeService, 
    private cd: ChangeDetectorRef,
    private dialog: MatDialog
  ) {
    this.dataSource = new MatTableDataSource<Employee>([]);
  }

  ngOnInit(): void {
    this.loadEmployees();
  }

  ngAfterViewInit(): void {
    console.log("AFTER VIEW INIT");
    this.dataSource.sort = this.sort;
  }

  private loadEmployees(resetPage: boolean = false): void {
    this.isLoading = true;
    const searchTerm = this.searchControl.value.toLowerCase().trim();

    if (resetPage) {
      this.pageIndex = 0;
    }

    this.employeeService.getEmployees(this.pageIndex, this.pageSize, searchTerm).subscribe({
      next: (response: EmployeePageResponse) => {
        this.allEmployees = response.content;
        this.filteredEmployees = response.content;
        
        this.totalElements = response.totalElements; 
        this.pageIndex = response.currentPage; 
        this.totalPages = response.totalPages;
        this.pageSize = response.pageSize;

        this.dataSource.data = response.content;
        this.isLoading = false;

        if (this.paginator) {
          this.paginator.length = this.totalElements; 
          this.paginator.pageIndex = this.pageIndex; 
        }
        this.cd.detectChanges();

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

  openAddEmployeeDialog(): void {
  const dialogRef = this.dialog.open(AddEmployeeDialogComponent, {
    width: '850px',
    maxWidth: '95vw',
    maxHeight: '90vh',
    disableClose: false,
    autoFocus: true,
    panelClass: ['add-employee-dialog']
  });

  dialogRef.afterClosed().subscribe(result => {
    if (result) {
      this.handleAddEmployee(result);
    }
  });
}


  private handleAddEmployee(employeeData: any): void {
    this.isLoading = true;

    // TODO: Replace with actual API call
    // this.employeeService.addEmployee(employeeData).subscribe({
    //   next: (response) => {
    //     this.showSnackBar('Employee added successfully', 'success');
    //     this.loadEmployees(true);
    //   },
    //   error: (error) => {
    //     this.showSnackBar('Failed to add employee: ' + error.message, 'error');
    //     this.isLoading = false;
    //   }
    // });

    // Mock API call
    setTimeout(() => {
      console.log('Employee data to be sent to backend:', employeeData);
      this.showSnackBar('Employee added successfully', 'success');
      this.loadEmployees(true);
    }, 1000);
  }

  onSearchClick(): void {
    const searchTerm = this.searchControl.value;

    if (!searchTerm.trim() && this.allEmployees.length > 0) {
      this.onResetSearch();
      return;
    }
    this.loadEmployees(true);
  }

  onResetSearch(): void {
    this.searchControl.setValue('');
    this.loadEmployees(true);
    this.showSnackBar('Search reset. Showing all employees.', 'info');
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadEmployees();
  }

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
    console.log(this.selectedFileName)
    this.isUploading = true;

    this.employeeService.uploadFile(file).subscribe({
      next: () => {
        this.showSnackBar('File uploaded successfully', 'success');   
        this.clearSelectedFile();
        this.isUploading = false;
        this.loadEmployees();
        input.value = '';
      },
      error: (error) => {
        this.showSnackBar('File upload failed: ' + error.message, 'error');
        this.isUploading = false;
        input.value = '';
      }
    });
  }

  clearSelectedFile(): void {
    this.selectedFileName = '';
  }

  downloadBulkTemplate(): void {
    const headers = ['employeeId', 'name', 'role', 'department', 'businessUnit'];
    const sampleRow = ['EMP001', 'John Doe', 'Senior Developer', 'Engineering', 'Technology'];
    
    let csvContent = headers.join(',') + '\n';
    csvContent += sampleRow.join(',') + '\n';

    this.downloadCSV(csvContent, 'employee_bulk_upload_template.csv');
    this.showSnackBar('Template downloaded successfully', 'success');
  }

  viewFullProfile(employee: Employee): void {
    console.log('View profile for:', employee);
    this.showSnackBar(`Profile view for ${employee.name} - Coming soon`, 'info');
  }

  removeEmployee(employee: Employee): void {
    console.log(`[Action] Attempting to remove employee ${employee.name}`);

    this.isLoading = true;

    setTimeout(() => {
      this.allEmployees = this.allEmployees.filter(e => e.employeeId !== employee.employeeId);
      this.showSnackBar(`Employee ${employee.employeeId} removed successfully (Mock)`, 'success');
      this.loadEmployees();
    }, 800);
  }

  private downloadCSV(content: string, filename: string): void {
    const blob = new Blob([content], { type: 'text/csv;charset=utf-8;' });
    this.downloadFile(blob, filename);
  }

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

  private showSnackBar(message: string, type: 'success' | 'error' | 'info'): void {
    this.mockSnackBar.open(message, 'Close', {
      duration: 5000,
      horizontalPosition: 'end',
      verticalPosition: 'top',
      panelClass: type === 'success' ? ['snackbar-success'] : type === 'error' ? ['snackbar-error'] : ['snackbar-info']
    });
  }

  getSerialNumber(index: number): number {
    return this.pageIndex * this.pageSize + index + 1;
  }
}