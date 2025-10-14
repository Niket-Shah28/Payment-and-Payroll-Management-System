package com.aurionpro.payrollsystem.dto.employeeAttendanceLeave;

import java.time.LocalDate;

import com.aurionpro.payrollsystem.entity.leave.LeaveStatus;
import com.aurionpro.payrollsystem.entity.leave.LeaveType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LeaveApplyRequestDto {

	
	//private Long managerId;
	
	@NotNull(message="Leave type id cannot be null")
	private Long leaveTypeId;
	
	@NotBlank(message="start date must be entered")
	private LocalDate startDate;
	
	@NotBlank(message="End date must be entered")
	private LocalDate endDate;
	
	@NotBlank(message="Reason must be entered")
	private String reason;
	
	@NotBlank
	private LeaveStatus leaveStatus;

}
