package com.aurionpro.payrollsystem.dto.organization;

import com.aurionpro.payrollsystem.entity.bankAccount.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class OrganizationUpdateBankAccountDto {
	private String accountNumber;
	private String accountHolderName;
	private String ifscCode;
	private AccountType accountType;
}
