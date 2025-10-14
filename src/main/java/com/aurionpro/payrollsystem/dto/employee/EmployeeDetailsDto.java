package com.aurionpro.payrollsystem.dto.employee;

import java.sql.Date;

import com.aurionpro.payrollsystem.entity.employee.Gender;
import com.aurionpro.payrollsystem.entity.employee.Salutation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class EmployeeDetailsDto {
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
}
