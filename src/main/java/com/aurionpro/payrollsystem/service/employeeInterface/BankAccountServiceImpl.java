package com.aurionpro.payrollsystem.service.employeeInterface;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aurionpro.payrollsystem.dto.employeeAccountDetails.BankAccountDetailsDto;
import com.aurionpro.payrollsystem.dto.employeeAccountDetails.BankAccountDetailsUpdateDto;
import com.aurionpro.payrollsystem.entity.bankAccount.BankAccountDetails;
import com.aurionpro.payrollsystem.repository.BankAccountDetailsRepository;

@Service
public class BankAccountServiceImpl implements BankAccountService  {
	
	@Autowired
	private  BankAccountDetailsRepository accountRepository;

	@Override
    public List<BankAccountDetailsDto> getAllAccountDetails() {
        return accountRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public BankAccountDetailsDto getAccountDetailsById(Long accountId) {
        BankAccountDetails account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));
        return convertToDto(account);
    }
    
    @Override
    @Transactional
    public BankAccountDetailsDto updateAccountDetails(Long accountId, BankAccountDetailsUpdateDto updateDto) {
        BankAccountDetails account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));
        
        // Check if account number is being changed and if it already exists
        if (updateDto.getAccountNumber() != null && 
            !updateDto.getAccountNumber().equals(account.getAccountNumber())) {
            if (accountRepository.existsByAccountNumber(updateDto.getAccountNumber())) {
                throw new RuntimeException("Account number already exists: " + updateDto.getAccountNumber());
            }
            account.setAccountNumber(updateDto.getAccountNumber());
        }
        
        // Update only non-null fields
        if (updateDto.getBankName() != null) {
            account.setBankName(updateDto.getBankName());
        }
        if (updateDto.getIfscCode() != null) {
            account.setIfscCode(updateDto.getIfscCode());
        }
        if (updateDto.getAccountType() != null) {
            account.setAccountType(updateDto.getAccountType());
        }
        if (updateDto.getAccountHolderName() != null) {
            account.setAccountHolderName(updateDto.getAccountHolderName());
        }
        if (updateDto.getIsActive() != null) {
            account.setIsActive(updateDto.getIsActive());
        }
        
        account.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        
        BankAccountDetails savedAccount = accountRepository.save(account);
        return convertToDto(savedAccount);
    }
    
    @Override
    public List<BankAccountDetailsDto> getAllActiveAccountDetails() {
        return accountRepository.findByIsActive(true)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    private BankAccountDetailsDto convertToDto(BankAccountDetails account) {
        BankAccountDetailsDto dto = new BankAccountDetailsDto();
        dto.setAccountId(account.getAccountId());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setBankName(account.getBankName());
        dto.setIfscCode(account.getIfscCode());
        dto.setAccountType(account.getAccountType());
        dto.setAccountHolderName(account.getAccountHolderName());
        dto.setIsActive(account.getIsActive());
        dto.setCreatedAt(account.getCreatedAt());
        dto.setUpdatedAt(account.getUpdatedAt());
        return dto;
    }
	
}
