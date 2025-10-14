package com.aurionpro.payrollsystem.service.employeeInterface;

import java.util.List;

import com.aurionpro.payrollsystem.dto.employeeAccountDetails.BankAccountDetailsDto;
import com.aurionpro.payrollsystem.dto.employeeAccountDetails.BankAccountDetailsUpdateDto;

public interface BankAccountService {
	
    List<BankAccountDetailsDto> getAllAccountDetails();
    

    BankAccountDetailsDto getAccountDetailsById(Long accountId);
    
    
    BankAccountDetailsDto updateAccountDetails(Long accountId, BankAccountDetailsUpdateDto updateDto);
    
    List<BankAccountDetailsDto> getAllActiveAccountDetails();

}
