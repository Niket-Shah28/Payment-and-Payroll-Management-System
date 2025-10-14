package com.aurionpro.payrollsystem.dto.manageEmployeeProfile;

import java.sql.Date;

import org.hibernate.validator.constraints.URL;

import com.aurionpro.payrollsystem.entity.employee.EmployeeSalary;
import com.aurionpro.payrollsystem.entity.employee.Gender;
import com.aurionpro.payrollsystem.entity.employee.Salutation;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ProfileRequestDto {
	
	
	@Size(max = 50, message = "First name cannot exceed 50 characters.")
	private String firstName;
	
	@Size(max = 50, message = "Middle name cannot exceed 50 characters.")
	private String middleName;
	
	
	@Size(max = 50, message = "Last name cannot exceed 50 characters.")
	private String lastName;
	
	@URL
	private String profilePhotoUrl;
	
	
	private Gender gender; 
	
	
	private Salutation salutation; 
	
	@Size(max = 100, message = "Spouse name is too long.")
	private String spouse;
	
	
	@Past(message = "Date of Birth must be in the past.")
	private Date dateOfBirth;
	
	
	private String bloodGroup;
	
	
	private String nationality;
	
	@Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "Invalid PAN number format.")
	private String panNumber;
	
	@Pattern(regexp = "^[0-9]{12}$", message = "Aadhar number must be 12 digits.")
	private String aadharNumber;
	
	private EmployeeSalary salary;
	  

}
