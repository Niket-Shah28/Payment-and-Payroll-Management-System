import { AttendanceStatus } from './attendance-status.model';

export interface AttendanceResponseDto {
    date: string;
    attendanceStatus: AttendanceStatus;
    employeeId: number;
}