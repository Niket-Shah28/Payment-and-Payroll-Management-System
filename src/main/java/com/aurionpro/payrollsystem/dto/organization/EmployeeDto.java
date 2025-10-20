package com.aurionpro.payrollsystem.dto.organization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class EmployeeDto {
    private Long employeeId;
    private String name;
    private String role;
    private String department;
    private String businessUnit;
}
