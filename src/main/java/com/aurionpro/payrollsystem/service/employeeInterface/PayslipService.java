package com.aurionpro.payrollsystem.service.employeeInterface;

import java.time.Month;
import java.util.List;
import java.util.Optional;

import com.aurionpro.payrollsystem.dto.employeePayslip.PayslipDto;
import com.aurionpro.payrollsystem.dto.employeePayslip.PayslipSummaryDto;
import com.aurionpro.payrollsystem.entity.employee.Employee;
import com.aurionpro.payrollsystem.entity.employee.Payslip;

public interface PayslipService {

//	Payslip generateAndSavePayslip(Employee employee, int month, int year) throws Exception;
//	
//	Optional<String> getPayslipUrl(Long employeeId, Month month, Integer year);
	
	//
	
	
	List<PayslipDto> getAllPayslipsByEmployeeId(Long employeeId);

	PayslipDto getPayslipById(Long payslipId);

	PayslipDto getPayslipByEmployeeIdMonthYear(Long employeeId, Month month, Integer year);

	List<PayslipDto> getPayslipsByEmployeeIdAndYear(Long employeeId, Integer year);

	PayslipDto getLatestPayslipByEmployeeId(Long employeeId);

	List<PayslipSummaryDto> getPayslipSummariesByEmployeeId(Long employeeId);

	//byte[] downloadPayslipPdf(Long payslipId);

	String getPayslipFilename(Long payslipId);
}
