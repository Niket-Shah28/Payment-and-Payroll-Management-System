package com.aurionpro.payrollsystem.service.employeeInterface;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import com.aurionpro.payrollsystem.dto.employeeAttendanceLeave.AttendanceMarkRequest;
import com.aurionpro.payrollsystem.dto.employeeAttendanceLeave.AttendanceResposeDto;
import com.aurionpro.payrollsystem.entity.employee.Payslip;

public interface AttendanceService {
	
	List<AttendanceResposeDto> markAttendance(Long employeeId, AttendanceMarkRequest request);

	 List<AttendanceResposeDto> getAttendanceForEmployee(Long employeeId, LocalDate from, LocalDate to);
	 
	


}
