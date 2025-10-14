package com.aurionpro.payrollsystem.dto.manageEmployeeProfile;

import jakarta.validation.constraints.Email;	
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class EmployeeContactDetailsRequestDto {

	//@NotBlank(message = "Personal email is mandatory.")
	@Email(message = "Must be a valid email address.")
	private String email;

	//@NotBlank(message = "Office email is mandatory.")
	@Email(message = "Must be a valid email address.")
	private String officeEmail;

	//@NotBlank(message = "Personal phone number is mandatory.")
	@Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits.")
	private String personalPhone;

	@Pattern(regexp = "^[0-9]{10}$", message = "Emergency contact number must be 10 digits.")
	private String emergencyContact;

	@Size(max = 50, message = "Emergency contact relation is too long.")
	private String emergencyContactRelation;

	@Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits.")
	private String alternateMobileNumber;

}
