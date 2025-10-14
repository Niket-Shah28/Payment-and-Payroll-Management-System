package com.aurionpro.payrollsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.payrollsystem.dto.employeeAccountDetails.EmployeeBankInfoDto;
import com.aurionpro.payrollsystem.dto.employeeAccountDetails.EmployeeBankInfoUpdateDto;
import com.aurionpro.payrollsystem.service.employeeInterface.EmployeeBankInfoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employee/bank-info")
public class EmployeeBankInfoController {

	@Autowired
	private EmployeeBankInfoService employeeBankInfoService;

	@GetMapping
	@PreAuthorize("hasRole('EMPLOYEE')")
	public ResponseEntity<List<EmployeeBankInfoDto>> getAllEmployeeBankInfo() {
		List<EmployeeBankInfoDto> bankInfoList = employeeBankInfoService.getAllEmployeeBankInfo();
		return ResponseEntity.ok(bankInfoList);
	}

	@GetMapping("/{bankInfoId}")
	public ResponseEntity<EmployeeBankInfoDto> getEmployeeBankInfoById(@PathVariable Long bankInfoId) {
		EmployeeBankInfoDto bankInfo = employeeBankInfoService.getEmployeeBankInfoById(bankInfoId);
		return ResponseEntity.ok(bankInfo);
	}

//	@GetMapping("/{employeeId}")
//	public ResponseEntity<EmployeeBankInfoDto> getEmployeeBankInfoByEmployeeId(@PathVariable Long employeeId) {
//		EmployeeBankInfoDto bankInfo = employeeBankInfoService.getEmployeeBankInfoByEmployeeId(employeeId);
//		return ResponseEntity.ok(bankInfo);
//	}

	@PatchMapping("/{bankInfoId}")
	@PreAuthorize("hasRole('EMPLOYEE')")
	public ResponseEntity<EmployeeBankInfoDto> updateEmployeeBankInfo(@PathVariable Long bankInfoId,
			@Valid @RequestBody EmployeeBankInfoUpdateDto updateDto) {
		EmployeeBankInfoDto updatedBankInfo = employeeBankInfoService.updateEmployeeBankInfo(bankInfoId, updateDto);
		return ResponseEntity.ok(updatedBankInfo);
	}

	@GetMapping("/active")
	@PreAuthorize("hasRole('EMPLOYEE')")
	public ResponseEntity<List<EmployeeBankInfoDto>> getAllActiveEmployeeBankInfo() {
		List<EmployeeBankInfoDto> bankInfoList = employeeBankInfoService.getAllActiveEmployeeBankInfo();
		return ResponseEntity.ok(bankInfoList);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

}
