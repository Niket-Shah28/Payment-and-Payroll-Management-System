package com.aurionpro.payrollsystem.service.employeeInterface;

import java.util.List;

import com.aurionpro.payrollsystem.dto.employeeAccountDetails.EmployeeBankInfoDto;
import com.aurionpro.payrollsystem.dto.employeeAccountDetails.EmployeeBankInfoUpdateDto;

public interface EmployeeBankInfoService {

	List<EmployeeBankInfoDto> getAllEmployeeBankInfo();

	EmployeeBankInfoDto getEmployeeBankInfoById(Long employeeBankInfoId);

	//EmployeeBankInfoDto getEmployeeBankInfoByEmployeeId(Long employeeId);

	EmployeeBankInfoDto updateEmployeeBankInfo(Long employeeBankInfoId, EmployeeBankInfoUpdateDto updateDto);

	List<EmployeeBankInfoDto> getAllActiveEmployeeBankInfo();
}
