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

  // ✅ List of national holidays (YYYY-MM-DD format)
  nationalHolidays: string[] = [
    '2025-01-25', // Republic Day
    '2025-08-14', // Independence Day
    '2025-10-01', // Gandhi Jayanti
    '2025-12-24', // Christmas
    '2025-10-21', // Diwali
    '2025-10-22', // Diwali
    '2025-10-23'  // Diwali
  ];

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
    const month = this.currentDate.getMonth() - 1;
    this.currentDate = new Date(year, month, 1);
    this.generateMonthDays(this.currentDate);
    this.loadAttendance();
  }

  nextMonth() {
    const year = this.currentDate.getFullYear();
    const month = this.currentDate.getMonth() + 1;
    this.currentDate = new Date(year, month, 1);
    this.generateMonthDays(this.currentDate);
    this.loadAttendance();
  }

  // ✅ Check if date is a weekend
  isWeekend(date: Date): boolean {
    const day = date.getDay();
    return day === 0 || day === 6; // Sunday or Saturday
  }

  // ✅ Check if date is a national holiday
  isNationalHoliday(date: Date): boolean {
    const key = date.toISOString().split('T')[0];
    return this.nationalHolidays.includes(key);
  }

  // ✅ Check if date is older than 1 week (before 7 days from today)
  isOlderThanOneWeek(date: Date): boolean {
    const today = new Date();
    const sevenDaysAgo = new Date();
    sevenDaysAgo.setDate(today.getDate() - 7); // 7 days ago

    // compare only date part (ignore time)
    const dateOnly = new Date(date.toDateString());
    const sevenDaysAgoOnly = new Date(sevenDaysAgo.toDateString());

    return dateOnly < sevenDaysAgoOnly;
  }

  // ✅ NEW: Check if date is in the future (after current date)
  isFutureDate(date: Date): boolean {
    const today = new Date();
    const dateOnly = new Date(date.toDateString());
    const todayOnly = new Date(today.toDateString());

    return dateOnly > todayOnly; // future date (tomorrow or later)
  }

  toggleDate(date: Date) {
    const key = date.toISOString().split('T')[0];

    // Prevent marking attendance on weekends
    if (this.isWeekend(date)) {
      alert('🚫 You cannot mark attendance on weekends!');
      return;
    }

    // Prevent marking attendance on national holidays
    if (this.isNationalHoliday(date)) {
      alert('🚫 You cannot mark attendance on national holidays!');
      return;
    }

    // Prevent marking attendance for dates older than one week
    if (this.isOlderThanOneWeek(date)) {
      alert('⚠️ You can only mark attendance for the past 7 days. Older dates are locked.');
      return;
    }

    // ✅ Prevent marking attendance for future dates
    if (this.isFutureDate(date)) {
      alert('🚫 You cannot mark attendance for future dates!');
      return;
    }

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

    this.attendanceService.getAttendance(
      firstDay.toISOString().split('T')[0],
      lastDay.toISOString().split('T')[0]
    ).subscribe((data: AttendanceResponseDto[]) => {
      this.attendanceMap.clear();
      data.forEach(d => this.attendanceMap.set(d.date, d.attendanceStatus));
    });
  }

  markAttendance() {
    if (this.selectedDates.length === 0) {
      alert('Please select at least one valid date!');
      return;
    }

    const request: AttendanceMarkRequest = {
      dates: this.selectedDates.map(d => d.toISOString().split('T')[0]),
      attendanceStatus: this.attendanceStatus
    };

    this.attendanceService.markAttendance(request).subscribe({
      next: (res: AttendanceResponseDto[]) => {
        res.forEach(r => this.attendanceMap.set(r.date, r.attendanceStatus));
        alert(`✅ Attendance marked successfully as "${this.attendanceStatus}" for ${this.selectedDates.length} day(s)!`);
        this.selectedDates = [];
      },
      error: (err) => {
        console.error(err);
        alert('❌ Failed to mark attendance!');
      }
    });
  }

  getDayClass(date: Date): string {
    const key = date.toISOString().split('T')[0];
    const status = this.attendanceMap.get(key);

    if (this.isWeekend(date)) return 'weekend'; // gray for weekends
    if (this.isNationalHoliday(date)) return 'holiday'; // red for holidays
    if (this.isOlderThanOneWeek(date)) return 'locked'; // faded gray for old dates
    if (this.isFutureDate(date)) return 'locked'; // same style for future dates
    if (this.isSelected(date)) return 'selected';

    if (!status) return '';

    switch (status) {
      case AttendanceStatus.PRESENT: return 'present';
      case AttendanceStatus.ABSENT: return 'absent';
      case AttendanceStatus.OUTDOOR: return 'outdoor';
      default: return '';
    }
  }

  get monthName(): string {
    return this.currentDate.toLocaleString('default', { month: 'long' });
  }

  get year(): number {
    return this.currentDate.getFullYear();
  }

  // Add these methods to your MarkAttendance component class

getTooltip(date: Date): string {
  const key = date.toISOString().split('T')[0];
  const status = this.attendanceMap.get(key);
  
  if (this.isWeekend(date)) return 'Weekend';
  if (this.isNationalHoliday(date)) return 'National Holiday';
  if (this.isOlderThanOneWeek(date)) return 'Locked (Older than 7 days)';
  if (this.isFutureDate(date)) return 'Future Date';
  if (status) return `Marked as ${status}`;
  
  return 'Click to select';
}

getPresentCount(): number {
  let count = 0;
  this.attendanceMap.forEach((status) => {
    if (status === AttendanceStatus.PRESENT) count++;
  });
  return count;
}

getAbsentCount(): number {
  let count = 0;
  this.attendanceMap.forEach((status) => {
    if (status === AttendanceStatus.ABSENT) count++;
  });
  return count;
}

getOutdoorCount(): number {
  let count = 0;
  this.attendanceMap.forEach((status) => {
    if (status === AttendanceStatus.OUTDOOR) count++;
  });
  return count;
}

getWorkingDaysCount(): number {
  return this.monthDays.filter(day => 
    !this.isWeekend(day) && !this.isNationalHoliday(day)
  ).length;
}
}
