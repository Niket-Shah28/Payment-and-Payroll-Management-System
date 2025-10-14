package com.aurionpro.payrollsystem.dto.employeeAccountDetails;

import com.aurionpro.payrollsystem.entity.bankAccount.AccountType;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class BankAccountDetailsUpdateDto {

	@Size(min = 9, max = 18, message = "Account number should be between 9 and 18 digits")
    private String accountNumber;
    
    @Size(min = 2, max = 100, message = "Bank name should be between 2 and 100 characters")
    private String bankName;
    
    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "Invalid IFSC code format")
    private String ifscCode;
    
    private AccountType accountType;
    
    @Size(min = 2, max = 100, message = "Account holder name should be between 2 and 100 characters")
    private String accountHolderName;
    
    private Boolean isActive;
}
