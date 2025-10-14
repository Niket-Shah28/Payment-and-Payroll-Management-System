package com.aurionpro.payrollsystem.dto.employeePayslip;

import java.time.Month;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PayslipDto {
  
    private Month month;
    private Integer year;
    private String payslipUrl;
    private String employeeName;

}
