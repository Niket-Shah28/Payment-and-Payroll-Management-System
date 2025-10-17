
import { AttendanceStatus } from './attendance-status.model';

export interface AttendanceMarkRequest {
    dates: string[]; // array of date strings in yyyy-MM-dd
    attendanceStatus: AttendanceStatus;
}
