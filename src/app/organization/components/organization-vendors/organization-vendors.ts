import { Component, computed, DestroyRef, effect, inject, signal } from '@angular/core';
import { OrganizationVendorsService } from '../../service/organization-vendors-service';
import { VendorRequestDto } from '../../dto/VendorRequestDto';
import { VendorResponseDto } from '../../dto/VendorResponseDto';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { BehaviorSubject, combineLatest, debounceTime, distinctUntilChanged, map, Observable, startWith } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-organization-vendors',
  standalone: false,
  templateUrl: './organization-vendors.html',
  styleUrls: ['./organization-vendors.css']
})
export class OrganizationVendors {
  vendors: VendorResponseDto[] = [];
  dataSource = new MatTableDataSource<VendorResponseDto>([]);
  displayedColumns: string[] = ['index', 'name', 'email', 'phoneNumber', 'gstin', 'pan', 'actions'];

  // --- Form ---
  vendorForm: FormGroup;
  searchControl = new FormControl('');

  // --- State ---
  isLoading = false;
  showAddModal = false;
  viewingVendor: VendorRequestDto | null = null;
  vendorToDelete: VendorResponseDto | null = null;
  errorMessage: string | null = null;
  selectedFile: File | null = null;
  fileError: string | null = null;
  isUploading = false;

  // --- Regex ---
  readonly REGEX = {
    CIN: /^[LU][0-9]{5}[A-Z]{2}[0-9]{4}[A-Z]{3}[0-9]{6}$/,
    EMAIL: /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/,
    PHONE: /^[6-9]{1}[0-9]{9}$/,
    GSTIN: /^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$/,
    PAN: /^[A-Z]{5}[0-9]{4}[A-Z]{1}$/,
    TAN: /^[A-Z]{4}[0-9]{5}[A-Z]{1}$/,
  };

  constructor(private fb: FormBuilder, private vendorService: OrganizationVendorsService) {
    this.vendorForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      cinNumber: ['', [Validators.required, Validators.pattern(this.REGEX.CIN)]],
      email: ['', [Validators.required, Validators.pattern(this.REGEX.EMAIL)]],
      phoneNumber: ['', [Validators.required, Validators.pattern(this.REGEX.PHONE)]],
      address: ['', [Validators.required]],
      gstin: ['', [Validators.required, Validators.pattern(this.REGEX.GSTIN)]],
      pan: ['', [Validators.required, Validators.pattern(this.REGEX.PAN)]],
      tan: ['', [Validators.required, Validators.pattern(this.REGEX.TAN)]],
      contractTitle: ['', [Validators.required]],
      startDate: ['', [Validators.required]],
      endDate: ['', [Validators.required]],
    });
  }

  get f() {
    return this.vendorForm.controls;
  }

  ngOnInit() {
    this.loadVendors();
    // --- Search Filter ---
    this.searchControl.valueChanges.subscribe(term => {
      this.applyFilter(term);
    });
  }

  applyFilter(term: string | null) {
    const lowerTerm = (term ?? '').trim().toLowerCase();
    const safeVendors = this.vendors ?? [];
    this.dataSource.data = safeVendors.filter(vendor =>
      [vendor.name, vendor.email, vendor.phoneNumber, vendor.gstin, vendor.pan]
        .some(field => (field ?? '').toLowerCase().includes(lowerTerm))
    );
  }


  // --- Load Vendors ---
  loadVendors() {
    this.isLoading = true;
    this.vendorService.getVendors().subscribe({
      next: (vendors:any) => {
        //console.log(vendors)
        this.vendors = vendors['vendors'];
        //console.log(this.vendors)
        this.dataSource.data = this.vendors;

        console.log(this.dataSource.data)

        this.applyFilter(this.searchControl.value);
        this.clearError();
        this.isLoading = false;
      },
      error: (err) => {
        this.handleError(`Failed to load vendors: ${err.message}`);
        this.isLoading = false;
      }
    });
  }

  // --- File Upload ---
  handleFileUpload(event: Event) {
    const element = event.currentTarget as HTMLInputElement;
    const file = element.files?.[0] ?? null;
    this.selectedFile = null;
    this.fileError = null;

    if (!file) return;
    // if (file.type !== 'application/pdf') {
    //   this.fileError = 'Only PDF files are allowed.';
    //   element.value = '';
    //   return;
    // }

    this.selectedFile = file;
  }

  // --- Add Vendor ---
  addVendor() {
    this.vendorForm.markAllAsTouched();
    if (this.vendorForm.invalid || !this.selectedFile) {
      this.handleError('Please correct all form errors and select a PDF.');
      return;
    }

    this.isUploading = true;
    this.vendorService.uploadFile(this.selectedFile).subscribe({
      next: (contractUrl) => {
        this.isUploading = false;
        const newVendor: VendorRequestDto = {
          ...this.vendorForm.value,
          contractDocumentUrl: contractUrl
        };

        this.vendorService.addVendor(newVendor).subscribe({
          next: () => {
            this.vendorForm.reset();
            this.selectedFile = null;
            this.showAddModal = false;
            this.loadVendors();
            this.handleError('Vendor added successfully!');
          },
          error: (err) => this.handleError(`Database save failed: ${err.message}`)
        });
      },
      error: (err) => {
        this.isUploading = false;
        this.handleError(`File upload failed: ${err.message}`);
      }
    });
  }

  // --- Delete Vendor ---
  openDeleteConfirmation(vendor: VendorResponseDto) { this.vendorToDelete = vendor; this.clearError(); }
  cancelDeleteConfirmation() { this.vendorToDelete = null; }
  confirmDelete() { 
    if (!this.vendorToDelete) return;
    console.log("Hello")
    this.vendorService.deleteVendor(this.vendorToDelete.vendorId).subscribe({
    next: () => { this.loadVendors(); this.vendorToDelete = null; },
    error: (err) => this.handleError(`Delete failed: ${err.message}`)
    });
  }

  // --- View / Modal ---
  viewVendor(vendorId:Number) { 
    this.vendorService.getVendorFullProfile(vendorId).subscribe({
      next: (vendor:VendorRequestDto) => {
        this.viewingVendor = vendor;
        this.clearError();  
      },
      error: (err) => this.handleError(`Failed to load vendor details: ${err.message}`)
    });
  }
  closeViewModal() { this.viewingVendor = null; }

  cancelAdd() {
    this.vendorForm.reset();
    this.selectedFile = null;
    this.fileError = null;
    this.isUploading = false;
    this.showAddModal = false;
  }

  get vendorDetails() {
    if (!this.viewingVendor) return [];
    return [
      { key: 'Vendor Name', value: this.viewingVendor.name },
      { key: 'Email', value: this.viewingVendor.email },
      { key: 'Phone', value: this.viewingVendor.phoneNumber },
      { key: 'Address', value: this.viewingVendor.address },
      { key: 'GSTIN', value: this.viewingVendor.gstin },
      { key: 'PAN', value: this.viewingVendor.pan },
      { key: 'CIN Number', value: this.viewingVendor.cinNumber },
      { key: 'TAN', value: this.viewingVendor.tan },
      { key: 'Contract Title', value: this.viewingVendor.contractTitle },
      { key: 'Contract Start Date', value: this.viewingVendor.startDate },
      { key: 'Contract End Date', value: this.viewingVendor.endDate },
      { key: 'Contract Document', value: this.viewingVendor.contractDocumentUrl },
    ];
  }

  openPdf() {
    const url = this.viewingVendor?.contractDocumentUrl;
    console.log(this.viewingVendor)
    if (url) {
      this.vendorService.openPdf(url);
    } else {
      console.warn('No contract document URL found.');
    }
  }


  // --- Error Handling ---
  handleError(message: string) { this.errorMessage = message; }
  clearError() { this.errorMessage = null; }
}
