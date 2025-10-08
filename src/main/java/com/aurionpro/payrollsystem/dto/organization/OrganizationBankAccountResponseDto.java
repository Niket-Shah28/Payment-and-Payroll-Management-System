package com.aurionpro.payrollsystem.dto.organization;

import com.aurionpro.payrollsystem.entity.bankAccount.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class OrganizationBankAccountResponseDto {
	private Long accountId;
	private String accountNumber;
	private String accountHolderName;
	private String ifscCode;
	private AccountType accountType;
	private Double balance;
}
