package com.aurionpro.payrollsystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aurionpro.payrollsystem.dto.employee.BusinessUnitDto;
import com.aurionpro.payrollsystem.dto.employee.DepartmentDto;
import com.aurionpro.payrollsystem.dto.employee.EmployeeDesignationUpdateDto;
import com.aurionpro.payrollsystem.dto.employee.EmployeeRequestDto;
import com.aurionpro.payrollsystem.dto.employee.EmployeeRoleDto;
import com.aurionpro.payrollsystem.dto.employee.EmployeeSalaryUpdateDto;
import com.aurionpro.payrollsystem.dto.organization.OrganizationBankAccountDto;
import com.aurionpro.payrollsystem.dto.organization.OrganizationBankAccountResponseDto;
import com.aurionpro.payrollsystem.dto.organization.OrganizationUpdateBankAccountDto;
import com.aurionpro.payrollsystem.entity.employee.Status;
import com.aurionpro.payrollsystem.service.organization.OrganizationService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/organization")
public class OrganizationController {
	
	@Autowired
	private OrganizationService organizationService;
	
	@PostMapping("/departments")
	@PreAuthorize("hasRole('ORGANIZATION')")
	public ResponseEntity<Void> addDepartment(@RequestPart(name = "departmentName") @NotBlank String departmentName, Authentication authentication) {
		Long organizationId = (Long)authentication.getDetails();
		organizationService.addDepartment(departmentName, organizationId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PostMapping("/businessUnits")
	@PreAuthorize("hasRole('ORGANIZATION')")
	public ResponseEntity<Void> addBusinessUnit(@RequestPart(name = "businessUnitName") @NotBlank String businessUnitName, Authentication authentication) {
		Long organizationId = (Long)authentication.getDetails();
		organizationService.addBusinessUnit(businessUnitName, organizationId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PostMapping("/roles")
	@PreAuthorize("hasRole('ORGANIZATION')")
	public ResponseEntity<Void> addEmployeeRole(@RequestPart(name = "role") @NotBlank String roleName, Authentication authentication) {
		Long organizationId = (Long)authentication.getDetails();
		organizationService.addEmployeeRoles(roleName, organizationId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping("/departments")
	@PreAuthorize("hasRole('ORGANIZATION')")
	public ResponseEntity<Map<String, List<DepartmentDto>>> getOrganizationDepartments(Authentication authentication) {
		Long organizationId = (Long) authentication.getDetails();
		return new ResponseEntity<>(Map.of("departments",organizationService.getOrganizationDepartments(organizationId)), HttpStatus.OK);
	}
	
	@GetMapping("/businessUnits")
	@PreAuthorize("hasRole('ORGANIZATION')")
	public ResponseEntity<Map<String, List<BusinessUnitDto>>> getOrganizationBusinessUnits(Authentication authentication) {
		Long organizationId = (Long) authentication.getDetails();
		return new ResponseEntity<>(Map.of("businessUnits",organizationService.getOrganizationBusinessUnits(organizationId)), HttpStatus.OK);
	}
	
	@GetMapping("/roles")
	@PreAuthorize("hasRole('ORGANIZATION')")
	public ResponseEntity<Map<String, List<EmployeeRoleDto>>> getOrganizationEmployeeRoles(Authentication authentication) {
		Long organizationId = (Long) authentication.getDetails();
		return new ResponseEntity<>(Map.of("roles",organizationService.getOrganizationRoles(organizationId)), HttpStatus.OK);
	}
	
	@PostMapping("/employees")
	@PreAuthorize("hasRole('ORGANIZATION')")
	public ResponseEntity<Void> addEmployees(MultipartFile file, Authentication authentication){
		Long organizationId = (Long) authentication.getDetails();
		organizationService.bulkUploadEmployeeData(file, organizationId);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
	
	@PatchMapping("/departments/{departmentId}")
	@PreAuthorize("hasRole('ORGANIZATION')")
	public ResponseEntity<Void> updateDepartment(@PathVariable Long departmentId, @RequestPart(name = "departmentName") @NotNull String departmentName){
		organizationService.updateDepartment(departmentId, departmentName);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PatchMapping("/businessUnits/{businessUnitId}")
	@PreAuthorize("hasRole('ORGANIZATION')")
	public ResponseEntity<Void> updateBusinessUnit(@PathVariable Long businessUnitId, @RequestPart(name = "businessUnitName") @NotNull String businessUnitName){
		organizationService.updateBusinessUnit(businessUnitId, businessUnitName);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PatchMapping("/roles/{roleId}")
	@PreAuthorize("hasRole('ORGANIZATION')")
	public ResponseEntity<Void> updateEmployeeRoles(@PathVariable Long roleId, @RequestPart(name = "role") @NotNull String role){
		organizationService.updateEmployeeRoles(roleId, role);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@DeleteMapping("/departments/{departmentId}")
	@PreAuthorize("hasRole('ORGANIZATION')")
	public ResponseEntity<Void> removeDepartment(@PathVariable Long departmentId){
		organizationService.removeDepartment(departmentId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@DeleteMapping("/businessUnits/{businessUnitId}")
	@PreAuthorize("hasRole('ORGANIZATION')")
	public ResponseEntity<Void> removeBusinessUnit(@PathVariable Long businessUnitId){
		organizationService.removeBusinessUnit(businessUnitId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@DeleteMapping("/roles/{roleId}")
	@PreAuthorize("hasRole('ORGANIZATION')")
	public ResponseEntity<Void> removeEmployeeRoles(@PathVariable Long roleId){
		organizationService.removeEmployeeRoles(roleId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping("/bankAccount")
	@PreAuthorize("hasRole('ORGANIZATION')")
	public ResponseEntity<?> getOrganizationBankAccount(Authentication authentication){
		Long organizationId = (Long) authentication.getDetails();
		OrganizationBankAccountResponseDto account = organizationService.getOrganizationBankAccount(organizationId);
		
		if (account == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                             .body(Map.of("message", "No bank account found for this organization"));
	    }

	    return ResponseEntity.ok(account);
	}
	
	@PostMapping("/bankAccount")
	@PreAuthorize("hasRole('ORGANIZATION')")
	public ResponseEntity<Void> addOrganizationBankAccount(Authentication authentication, @RequestBody @Valid OrganizationBankAccountDto dto){
		Long organizationId = (Long) authentication.getDetails();
		organizationService.addOrganizationBankAccount(organizationId, dto);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PatchMapping("/bankAccount/{accountId}")
	@PreAuthorize("hasRole('ORGANIZATION')")
	public ResponseEntity<Void> updateOrganizationBankAccount(@PathVariable(name = "accountId") Long accountId, @RequestBody OrganizationUpdateBankAccountDto dto){
		organizationService.updateBankAccount(accountId, dto);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@DeleteMapping("/bankAccount/{accountId}")
	@PreAuthorize("hasRole('ORGANIZATION')")
	public ResponseEntity<Void> removeOrganizationBankAccount(@PathVariable(name = "accountId") Long accountId){
		organizationService.removeOrganizationBankAccount(accountId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PostMapping("/bankAccount/{accountId}/deposit")
	@PreAuthorize("hasRole('ORGANIZATION')")
	public ResponseEntity<Void> depositOrganizationBankAccount(
	        @PathVariable Long accountId,
	        @RequestParam("amount") double amount) {
	    organizationService.depositAmount(accountId, amount);
	    return ResponseEntity.ok().build();
	}
	
	@PostMapping("/paymentRequest/{requestId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> acceptPaymentRequest(@PathVariable Long requestId, @RequestParam Status status){
		organizationService.processPaymentRequest(requestId, status);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PostMapping("/employee")
	@PreAuthorize("hasRole('ORGANIZATION')")
	public ResponseEntity<Void> addSingleEmployee(@RequestBody @Valid EmployeeRequestDto dto, Authentication authentication){
		Long organizationId = (Long) authentication.getDetails();
		organizationService.addSingleEmployee(organizationId, dto);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PatchMapping("/employees/{employeeId}/salary")
	@PreAuthorize("hasRole('ORGANIZATION')")
	public ResponseEntity<Void> updateEmployeeSalaryDetails(@PathVariable Long employeeId, @RequestBody @Valid EmployeeSalaryUpdateDto dto){
		organizationService.updateEmployeeSalary(employeeId, dto);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PutMapping("/employees/{employeeId}/designation")
	@PreAuthorize("hasRole('ORGANIZATION')")
	public ResponseEntity<Void> updateEmployeeDesignationDetails(@PathVariable Long employeeId, @RequestBody @Valid EmployeeDesignationUpdateDto dto){
		organizationService.updateEmployeeDesignation(employeeId, dto);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
