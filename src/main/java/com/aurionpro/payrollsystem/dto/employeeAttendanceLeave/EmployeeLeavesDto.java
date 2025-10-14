package com.aurionpro.payrollsystem.dto.employeeAttendanceLeave;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class EmployeeLeavesDto {

	private Long leaveTypeId;
	private int remainingCount;
	private int totalCount;

}
