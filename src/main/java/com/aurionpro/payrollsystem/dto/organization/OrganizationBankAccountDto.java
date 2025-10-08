package com.aurionpro.payrollsystem.dto.organization;

import com.aurionpro.payrollsystem.entity.bankAccount.AccountType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class OrganizationBankAccountDto {
	@NotBlank
	private String accountNumber;
	@NotBlank
	private String accountHolderName;
	@NotBlank
	private String ifscCode;
	@NotNull
	private AccountType accountType;
	@Min(1)
	private Double balance;
}
