import { Component } from '@angular/core';
import { PayslipService } from '../../services/payslip-service'; 
import { PayslipDetail } from '../../dto/payslip-detail.model'; 

@Component({
  selector: 'app-payslip',
  standalone: false,
  templateUrl: './payslip.html',
  styleUrl: './payslip.css'
})
export class Payslip {

   months = [
    'JANUARY','FEBRUARY','MARCH','APRIL','MAY','JUNE',
    'JULY','AUGUST','SEPTEMBER','OCTOBER','NOVEMBER','DECEMBER'
  ];

  selectedMonth: string = '';
  selectedYear: number | null = null;
  payslip?: PayslipDetail;
  isLoading = false;

  constructor(private payslipService: PayslipService) {}

  viewPayslip() {
    if (!this.selectedMonth || !this.selectedYear) {
      alert('Please select both month and year!');
      return;
    }

    this.isLoading = true;
    this.payslipService.getPayslip(this.selectedMonth, this.selectedYear).subscribe({
      next: (data) => {
        this.payslip = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error(err);
        alert('Payslip not found for the selected period!');
        this.isLoading = false;
      }
    });
  }

  downloadPayslip() {
    if (!this.selectedMonth || !this.selectedYear) {
      alert('Please select month and year first!');
      return;
    }

    this.payslipService.downloadPayslip(this.selectedMonth, this.selectedYear).subscribe((blob) => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = `Payslip_${this.selectedMonth}_${this.selectedYear}.pdf`;
      a.click();
      window.URL.revokeObjectURL(url);
    });
  }

}
