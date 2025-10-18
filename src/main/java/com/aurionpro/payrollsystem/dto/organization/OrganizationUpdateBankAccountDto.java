package com.aurionpro.payrollsystem.dto.organization;

import com.aurionpro.payrollsystem.entity.bankAccount.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class OrganizationUpdateBankAccountDto {
	private String accountNumber;
	private String accountHolderName;
	private String ifscCode;
	private AccountType accountType;
	private Double balance;
}
