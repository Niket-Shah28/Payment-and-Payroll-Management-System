package com.aurionpro.payrollsystem.service.employeeInterface;

import java.util.List;

import com.aurionpro.payrollsystem.dto.employeeAttendanceLeave.LeaveApplyRequestDto;
import com.aurionpro.payrollsystem.dto.employeeAttendanceLeave.LeaveDecisionDto;
import com.aurionpro.payrollsystem.dto.employeeAttendanceLeave.LeaveResponseDto;

public interface LeaveService {
	
	LeaveResponseDto applyLeave(Long employeeId, LeaveApplyRequestDto dto);
    List<LeaveResponseDto> getLeavesForEmployee(Long employeeId);
    List<LeaveResponseDto> getLeavesForManager(Long managerId);
    LeaveResponseDto decideLeave(LeaveDecisionDto dto, Long managerId);

}
