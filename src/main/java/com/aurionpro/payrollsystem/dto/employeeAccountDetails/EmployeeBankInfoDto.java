package com.aurionpro.payrollsystem.dto.employeeAccountDetails;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class EmployeeBankInfoDto {
	private Long employeeBankInfoId;
    private Long employeeId;
    private String pfNumber;
    private String uanNumber;
    private BankAccountDetailsDto accountDetails;
    private Boolean isActive;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
