package com.aurionpro.payrollsystem.dto.employeePayslip;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.time.Month;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PayslipRequestDto {
	
	@NotBlank(message="Month is required")
	private Month month;
	
	@NotBlank(message="Year is required")
	private Integer year;

}
