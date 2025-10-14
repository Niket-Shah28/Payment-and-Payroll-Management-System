package com.aurionpro.payrollsystem.dto.employee;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class EmployeeSalaryUpdateDto {
	@Min(value = 0, message = "Basic Salary must be greater than zero")
	private Double basicSalary;

	@Min(value = 0, message = "House Rent Allowance must be greater than zero")
	private Double houseRentAllowance;

	@Min(value = 0, message = "Dearness Allowance must be greater than zero")
	private Double dearnessAllowance;

	@Min(value = 0, message = "Provident Fund must be greater than zero")
	private Double providentFund;
	
	@Min(value = 0, message = "Other Allowance must be greater than zero")
	private Double otherAllowance;

	@Min(value = 0, message = "Final Salary must be greater than zero")
	private Double finalSalary;
}
