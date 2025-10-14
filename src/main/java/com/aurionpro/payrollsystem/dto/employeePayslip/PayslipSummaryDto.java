package com.aurionpro.payrollsystem.dto.employeePayslip;

import java.time.Month;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PayslipSummaryDto {

	private Long payslipId;
	private Month month;
	//private String monthName;
	private Integer year;
	private String period; // e.g., "January 2025"
	//private Timestamp createdAt;

}
