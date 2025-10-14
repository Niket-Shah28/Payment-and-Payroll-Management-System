package com.aurionpro.payrollsystem.dto.employeeAttendanceLeave;

import java.time.LocalDate;
import java.util.List;

import com.aurionpro.payrollsystem.entity.attendance.AttendanceStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AttendanceMarkRequest {

//	private Long employeeId;
	private List<LocalDate> dates; // multiple dates' attendances
	private AttendanceStatus attendanceStatus; // PRESENT/ABSENT/OUTDOOR/UNPAID_LEAVE

}
