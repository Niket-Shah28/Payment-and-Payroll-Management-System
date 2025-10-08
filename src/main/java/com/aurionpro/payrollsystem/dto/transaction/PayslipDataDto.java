package com.aurionpro.payrollsystem.dto.transaction;

import java.time.Month;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PayslipDataDto {
	private Long employeeId;
	private String name;
	private String department;
	private String role;
	private String businessUnit;
	private String accountNumber;
	private String ifscCode;
	private String bankName;
	private Double basicSalary;
	private Double houseRentAllowance;
	private Double dearnessAllowance;
	private Double providentFund;
	private Double otherAllowance;
	private Double actualSalary;
	private Double finalSalary;
    private Month month;
    private Integer year;
}
