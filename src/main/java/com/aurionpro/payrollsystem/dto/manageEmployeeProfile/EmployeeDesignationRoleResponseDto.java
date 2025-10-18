package com.aurionpro.payrollsystem.dto.manageEmployeeProfile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class EmployeeDesignationRoleResponseDto {

	private String departmentName;
	private String businessUnitName;
	private String roleName;
	private Integer grade;

}
