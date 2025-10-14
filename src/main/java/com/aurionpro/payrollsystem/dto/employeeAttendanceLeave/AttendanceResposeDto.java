package com.aurionpro.payrollsystem.dto.employeeAttendanceLeave;

import java.time.LocalDate;

import com.aurionpro.payrollsystem.entity.attendance.AttendanceStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AttendanceResposeDto {

	private LocalDate date;
	private AttendanceStatus attendanceStatus;;
	private Long employeeId;;

}
