package com.aurionpro.payrollsystem.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeTransactionDataDto {
	private Long employeeId;
	private String accountNumber;
	private String ifscCode;
	private String accountHolderName;
	private String bankName;
	private Double amount;
}
