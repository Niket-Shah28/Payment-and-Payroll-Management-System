package com.aurionpro.payrollsystem.dto.transaction;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class FailedTransactionRecipientDto implements Serializable{
	private Long employeeId;
	private String name;
	private String accountNumber;
	private String ifscCode;
	private String bankName;
	private Double amount;
	private String failureReason;
}
