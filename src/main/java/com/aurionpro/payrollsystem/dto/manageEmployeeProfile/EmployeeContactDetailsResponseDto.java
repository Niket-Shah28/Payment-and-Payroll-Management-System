package com.aurionpro.payrollsystem.dto.manageEmployeeProfile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class EmployeeContactDetailsResponseDto {

	private String email;

	private String officeEmail;

	private String phoneNumber;

	private String emergencyContact;

	private String emergencyContactRelation;

	private String alternateMobileNumber;

}
