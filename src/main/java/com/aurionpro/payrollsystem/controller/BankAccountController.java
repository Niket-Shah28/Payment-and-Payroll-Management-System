package com.aurionpro.payrollsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.payrollsystem.dto.employeeAccountDetails.BankAccountDetailsDto;
import com.aurionpro.payrollsystem.dto.employeeAccountDetails.BankAccountDetailsUpdateDto;
import com.aurionpro.payrollsystem.service.employeeInterface.BankAccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employee/bank-account")
@CrossOrigin(origins="http://localhost:4200")
public class BankAccountController {

	@Autowired
	private BankAccountService bankAccountService;

	@GetMapping
	@PreAuthorize("hasRole('EMPLOYEE')")
	public ResponseEntity<List<BankAccountDetailsDto>> getAllAccountDetails() {
		List<BankAccountDetailsDto> accounts = bankAccountService.getAllAccountDetails();
		return ResponseEntity.ok(accounts);
	}

	@GetMapping("/{accountId}")
	@PreAuthorize("hasRole('EMPLOYEE')")
	public ResponseEntity<BankAccountDetailsDto> getAccountDetailsById(@PathVariable Long accountId) {
		BankAccountDetailsDto account = bankAccountService.getAccountDetailsById(accountId);
		return ResponseEntity.ok(account);
	}

	@PatchMapping("/{accountId}")
	@PreAuthorize("hasRole('EMPLOYEE')")
	public ResponseEntity<BankAccountDetailsDto> updateAccountDetails(@PathVariable Long accountId,
			@Valid @RequestBody BankAccountDetailsUpdateDto updateDto) {
		BankAccountDetailsDto updatedAccount = bankAccountService.updateAccountDetails(accountId, updateDto);
		return ResponseEntity.ok(updatedAccount);
	}

	@GetMapping("/active")
	@PreAuthorize("hasRole('EMPLOYEE')")
	public ResponseEntity<List<BankAccountDetailsDto>> getAllActiveAccountDetails() {
		List<BankAccountDetailsDto> accounts = bankAccountService.getAllActiveAccountDetails();
		return ResponseEntity.ok(accounts);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

}
