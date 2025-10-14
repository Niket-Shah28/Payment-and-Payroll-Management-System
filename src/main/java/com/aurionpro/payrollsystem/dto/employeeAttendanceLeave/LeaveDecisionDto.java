package com.aurionpro.payrollsystem.dto.employeeAttendanceLeave;

import com.aurionpro.payrollsystem.entity.leave.LeaveStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LeaveDecisionDto {

	@NotNull(message="Leave Request Id of employee cannot be null")
	private Long leaveRequestId;
	
	@NotBlank
	private LeaveStatus leaveStatus;
	
//	@NotBlank
//	private Long employeeId;

}
