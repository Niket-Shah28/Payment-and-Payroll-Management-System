package com.aurionpro.payrollsystem.dto.employee;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class EmployeeDesignationUpdateDto {
	@NotNull
	private Long businessUnitId;
	@NotNull
	private Long departmentId;
	@NotNull
	private Long employeeRoleId;
	@NotNull
	private Integer grade;
}
