package com.aurionpro.payrollsystem.service.employeeInterface;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aurionpro.payrollsystem.dto.employeeAccountDetails.BankAccountDetailsDto;
import com.aurionpro.payrollsystem.dto.employeeAccountDetails.EmployeeBankInfoDto;
import com.aurionpro.payrollsystem.dto.employeeAccountDetails.EmployeeBankInfoUpdateDto;
import com.aurionpro.payrollsystem.entity.bankAccount.BankAccountDetails;
import com.aurionpro.payrollsystem.entity.bankAccount.EmployeeBankInfo;
import com.aurionpro.payrollsystem.repository.EmployeeBankInfoRepository;

@Service
public class EmployeeBankInfoServiceImpl implements EmployeeBankInfoService{

	@Autowired
	private EmployeeBankInfoRepository employeeBankInfoRepository;
	
	 @Override
	    public List<EmployeeBankInfoDto> getAllEmployeeBankInfo() {
	        return employeeBankInfoRepository.findAll()
	                .stream()
	                .map(this::convertToDto)
	                .collect(Collectors.toList());
	    }
	    
	    @Override
	    public EmployeeBankInfoDto getEmployeeBankInfoById(Long employeeBankInfoId) {
	        EmployeeBankInfo bankInfo = employeeBankInfoRepository.findById(employeeBankInfoId)
	                .orElseThrow(() -> new RuntimeException("Employee bank info not found with id: " + employeeBankInfoId));
	        return convertToDto(bankInfo);
	    }
	    
//	    @Override
//	    public EmployeeBankInfoDto getEmployeeBankInfoByEmployeeId(Long employeeId) {
//	        EmployeeBankInfo bankInfo = employeeBankInfoRepository.findByEmployee_EmployeeId(employeeId)
//	                .orElseThrow(() -> new RuntimeException("Employee bank info not found for employee id: " + employeeId));
//	        return convertToDto(bankInfo);
//	    }
	    
	    @Override
	    @Transactional
	    public EmployeeBankInfoDto updateEmployeeBankInfo(Long employeeBankInfoId, EmployeeBankInfoUpdateDto updateDto) {
	        EmployeeBankInfo bankInfo = employeeBankInfoRepository.findById(employeeBankInfoId)
	                .orElseThrow(() -> new RuntimeException("Employee bank info not found with id: " + employeeBankInfoId));
	        
	        // Check if PF number is being changed and if it already exists
	        if (updateDto.getPfNumber() != null && 
	            !updateDto.getPfNumber().equals(bankInfo.getPfNumber())) {
	            if (employeeBankInfoRepository.existsByPfNumber(updateDto.getPfNumber())) {
	                throw new RuntimeException("PF number already exists: " + updateDto.getPfNumber());
	            }
	            bankInfo.setPfNumber(updateDto.getPfNumber());
	        }
	        
	        // Check if UAN number is being changed and if it already exists
	        if (updateDto.getUanNumber() != null && 
	            !updateDto.getUanNumber().equals(bankInfo.getUanNumber())) {
	            if (employeeBankInfoRepository.existsByUanNumber(updateDto.getUanNumber())) {
	                throw new RuntimeException("UAN number already exists: " + updateDto.getUanNumber());
	            }
	            bankInfo.setUanNumber(updateDto.getUanNumber());
	        }
	        
	        // Update only non-null fields
	        if (updateDto.getIsActive() != null) {
	            bankInfo.setIsActive(updateDto.getIsActive());
	        }
	        
	        bankInfo.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
	        
	        EmployeeBankInfo savedBankInfo = employeeBankInfoRepository.save(bankInfo);
	        return convertToDto(savedBankInfo);
	    }
	    
	    @Override
	    public List<EmployeeBankInfoDto> getAllActiveEmployeeBankInfo() {
	        return employeeBankInfoRepository.findByIsActive(true)
	                .stream()
	                .map(this::convertToDto)
	                .collect(Collectors.toList());
	    }
	    
	    private EmployeeBankInfoDto convertToDto(EmployeeBankInfo bankInfo) {
	        EmployeeBankInfoDto dto = new EmployeeBankInfoDto();
	        dto.setEmployeeBankInfoId(bankInfo.getEmployeeBankInfoId());
	        dto.setEmployeeId(bankInfo.getEmployee().getEmployeeId());
	        dto.setPfNumber(bankInfo.getPfNumber());
	        dto.setUanNumber(bankInfo.getUanNumber());
	        dto.setAccountDetails(convertAccountToDto(bankInfo.getAccount()));
	        dto.setIsActive(bankInfo.getIsActive());
	        dto.setCreatedAt(bankInfo.getCreatedAt());
	        dto.setUpdatedAt(bankInfo.getUpdatedAt());
	        return dto;
	    }
	    
	    private BankAccountDetailsDto convertAccountToDto(BankAccountDetails account) {
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
