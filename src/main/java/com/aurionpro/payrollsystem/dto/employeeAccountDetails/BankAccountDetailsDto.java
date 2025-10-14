package com.aurionpro.payrollsystem.dto.employeeAccountDetails;

import java.sql.Timestamp;

import com.aurionpro.payrollsystem.entity.bankAccount.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class BankAccountDetailsDto {
    private Long accountId;
    private String accountNumber;
    private String bankName;
    private String ifscCode;
    private AccountType accountType;
    private String accountHolderName;
    private Boolean isActive;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}