import { Component } from '@angular/core';
import { AttendanceService } from '../../../services/attendance-service'; 
import { AttendanceStatus } from '../../../dto/attendance-status.model'; 
import { AttendanceMarkRequest } from '../../../dto/attendance-mark-request.model'; 
import { AttendanceResponseDto } from '../../../dto/attendance-response.model';

@Component({
  selector: 'app-mark-attendance',
  standalone: false,
  templateUrl: './mark-attendance.html',
  styleUrls: ['./mark-attendance.css']
})
export class MarkAttendance {
 selectedDates: Date[] = [];
  attendanceStatus: AttendanceStatus = AttendanceStatus.PRESENT;
  statusOptions = Object.values(AttendanceStatus);

  monthDays: Date[] = [];
  currentDate: Date = new Date();

  attendanceMap: Map<string, AttendanceStatus> = new Map(); // key = yyyy-mm-dd

  constructor(private attendanceService: AttendanceService) {}

  ngOnInit(): void {
    this.generateMonthDays(this.currentDate);
    this.loadAttendance();
  }

  generateMonthDays(date: Date) {
    const year = date.getFullYear();
    const month = date.getMonth();
    const daysInMonth = new Date(year, month + 1, 0).getDate();
    this.monthDays = [];
    for (let i = 1; i <= daysInMonth; i++) {
      this.monthDays.push(new Date(year, month, i));
    }
  }

  prevMonth() {
  const year = this.currentDate.getFullYear();
  const month = this.currentDate.getMonth() - 1; // go to previous month
  this.currentDate = new Date(year, month, 1);   // create new Date object
  this.generateMonthDays(this.currentDate);
  this.loadAttendance();
}

nextMonth() {
  const year = this.currentDate.getFullYear();
  const month = this.currentDate.getMonth() + 1; // go to next month
  this.currentDate = new Date(year, month, 1);   // create new Date object
  this.generateMonthDays(this.currentDate);
  this.loadAttendance();
}


  toggleDate(date: Date) {
    const key = date.toISOString().split('T')[0];
    const index = this.selectedDates.findIndex(d => d.toISOString().split('T')[0] === key);
    if (index > -1) this.selectedDates.splice(index, 1);
    else this.selectedDates.push(date);
  }

  isSelected(date: Date): boolean {
    const key = date.toISOString().split('T')[0];
    return this.selectedDates.some(d => d.toISOString().split('T')[0] === key);
  }

  loadAttendance() {
    const firstDay = new Date(this.currentDate.getFullYear(), this.currentDate.getMonth(), 1);
    const lastDay = new Date(this.currentDate.getFullYear(), this.currentDate.getMonth() + 1, 0);

    // Convert Date -> string before passing to service
    this.attendanceService.getAttendance(firstDay.toISOString().split('T')[0], lastDay.toISOString().split('T')[0])
      .subscribe((data: AttendanceResponseDto[]) => {
        this.attendanceMap.clear();
        data.forEach(d => this.attendanceMap.set(d.date, d.attendanceStatus));
      });
  }

  markAttendance() {
    if (this.selectedDates.length === 0) {
      alert('Please select at least one date!');
      return;
    }

    const request: AttendanceMarkRequest = {
      dates: this.selectedDates.map(d => d.toISOString().split('T')[0]), // convert Date -> string
      attendanceStatus: this.attendanceStatus
    };

    this.attendanceService.markAttendance(request).subscribe({
      next: (res: AttendanceResponseDto[]) => {
        res.forEach(r => this.attendanceMap.set(r.date, r.attendanceStatus));
        alert(`Attendance marked successfully as "${this.attendanceStatus}" for ${this.selectedDates.length} day(s)!`);
        this.selectedDates = [];
      },
      error: (err) => {
        console.error(err);
        alert('Failed to mark attendance!');
      }
    });
  }

  getDayClass(date: Date): string {
    const key = date.toISOString().split('T')[0];
    const status = this.attendanceMap.get(key);

    if (this.isSelected(date)) return 'selected';
    if (!status) return '';

    switch (status) {
      case AttendanceStatus.PRESENT: return 'present';
      case AttendanceStatus.ABSENT: return 'absent';
      case AttendanceStatus.LEAVE: return 'leave';
      case AttendanceStatus.OUTDOOR: return 'outdoor';
      case AttendanceStatus.UNPAID_LEAVE: return 'unpaid-leave';
      default: return '';
    }
  }

  get monthName(): string {
    return this.currentDate.toLocaleString('default', { month: 'long' });
  }

  get year(): number {
    return this.currentDate.getFullYear();
  }

}
