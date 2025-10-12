package com.aurionpro.payrollsystem.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class EmployeeRoleDto {
	private Long roleId;
	private String role;
}
