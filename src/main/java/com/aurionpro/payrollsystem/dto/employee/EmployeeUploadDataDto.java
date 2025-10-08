package com.aurionpro.payrollsystem.dto.employee;

import java.sql.Date;

import com.aurionpro.payrollsystem.entity.employee.Gender;
import com.aurionpro.payrollsystem.entity.employee.Salutation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeUploadDataDto {
	private String firstName;
	private String middleName;
	private String lastName;
	private Gender gender;
	private Salutation salutation;
	private String spouse;
	private Date dateOfBirth;
	private String bloodGroup;
	private String nationality;
	private String panNumber;
	private String aadharNumber;
	private String managerEmail;
	private String officeEmail;
	private String personalEmail;
	private String personalPhoneNumber;
	private Double basicSalary;
	private Double houseRentAllowance;
	private Double dearnessAllowance;
	private Double providentFund;
	private Double otherAllowance;
	private Double finalSalary;
	private Integer grade;
	private String employeeRole;
	private String businessUnit;
	private String department;
}
