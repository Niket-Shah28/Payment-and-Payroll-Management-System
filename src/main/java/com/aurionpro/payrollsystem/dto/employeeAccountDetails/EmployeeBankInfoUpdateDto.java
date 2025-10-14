package com.aurionpro.payrollsystem.dto.employeeAccountDetails;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class EmployeeBankInfoUpdateDto {

	@Pattern(regexp = "^[A-Z]{2}/[A-Z]{3}/\\d{7}/\\d{3}/\\d{7}$", message = "Invalid PF number format")
	private String pfNumber;

	@Pattern(regexp = "^\\d{12}$", message = "UAN number must be 12 digits")
	private String uanNumber;

	private Boolean isActive;
}
