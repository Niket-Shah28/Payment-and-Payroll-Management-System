package com.aurionpro.payrollsystem.dto.employeePayslip;

import java.time.Month;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayslipDetailDto {
    // Payslip Period
    private Month payslipMonth;
    private Integer year;
    
    // Employee Details
    private Long employeeId;
    private String employeeName;
    private String department;
    private String role;
    private String businessUnit;
    
    // Bank Details
    private String accountNumber;
    private String ifscCode;
    private String bankName;
    
    // Salary Components (Earnings)
    private Double basicSalary;
    private Double houseRentAllowance;
    private Double dearnessAllowance;
    private Double otherAllowances;
    private Double actualSalary; // Total Earnings (Basic + HRA + DA + Other)

    // Deductions
    private Double providentFund;
    
    // Final Salary
    private Double finalSalary; // In Hand (Actual Salary - Deductions)
}
