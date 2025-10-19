package com.aurionpro.payrollsystem.service.organization;

import java.util.List;

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
import com.aurionpro.payrollsystem.dto.payment.PaymentRequestDto;
import com.aurionpro.payrollsystem.dto.vendor.VendorDto;
import com.aurionpro.payrollsystem.dto.vendor.VendorResponseDto;
import com.aurionpro.payrollsystem.entity.employee.Status;

public interface OrganizationService {
	void addDepartment(String departmentName, Long organizationId);
	void addBusinessUnit(String businessUnitName, Long organizationId);
	void addEmployeeRoles(String role, Long organizationId);
	List<DepartmentDto> getOrganizationDepartments(Long organizationId);
	List<BusinessUnitDto> getOrganizationBusinessUnits(Long organizationId);
	List<EmployeeRoleDto> getOrganizationRoles(Long organizationId);
    void bulkUploadEmployeeData(MultipartFile file, Long organizationId);
	void updateDepartment(Long departmentId, String departmentName);
	void updateBusinessUnit(Long businessUnitId, String businessUnitName);
	void updateEmployeeRoles(Long roleId, String role);
	void removeDepartment(Long departmentId);
	void removeBusinessUnit(Long businessUnitId);
	void removeEmployeeRoles(Long roleId);
	void processPaymentRequest(Long paymentRequestId, Status status);
	void addOrganizationBankAccount(Long organizationId, OrganizationBankAccountDto dto);
	void updateBankAccount(Long accountId, OrganizationUpdateBankAccountDto dto);
	void removeOrganizationBankAccount(Long accountId);
	OrganizationBankAccountResponseDto getOrganizationBankAccount(Long organizationId);
	void depositAmount(Long accountId, Double amount);
	void addSingleEmployee(Long organizationId, EmployeeRequestDto emp);
	void updateEmployeeSalary(Long employeeId, EmployeeSalaryUpdateDto dto);
	void updateEmployeeDesignation(Long employeeId, EmployeeDesignationUpdateDto dto);
	void removeEmployee(Long employeeId);
	void addVendor(VendorDto dto, Long organizationId);
	List<VendorResponseDto> getVendors(Long organizationId);
	void removeVendor(Long vendorId);
	VendorDto getVendor(Long vendorId);
	void addPaymentRequest(Long organizationId, PaymentRequestDto dto);
}
