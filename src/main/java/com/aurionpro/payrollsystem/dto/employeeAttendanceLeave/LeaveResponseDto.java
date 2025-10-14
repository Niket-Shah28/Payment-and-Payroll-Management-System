package com.aurionpro.payrollsystem.dto.employeeAttendanceLeave;

import java.time.LocalDate;

import com.aurionpro.payrollsystem.entity.employee.Employee;
import com.aurionpro.payrollsystem.entity.leave.EmployeeLeaves;
import com.aurionpro.payrollsystem.entity.leave.LeaveStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class LeaveResponseDto {
	
	private Long leaveRequestId;
    private Long employeeId;
    private Long managerId;
    private Long leaveTypeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveStatus leaveStatus; // Pending/Approved/Rejected
    private String reason;
   private EmployeeLeaves remainingDays;
    //private EmployeeLeavesDto employeeLeaves;

}
