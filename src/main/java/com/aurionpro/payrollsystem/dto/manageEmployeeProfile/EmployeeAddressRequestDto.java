package com.aurionpro.payrollsystem.dto.manageEmployeeProfile;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class EmployeeAddressRequestDto {

	@Size(max = 300)
	private String currentAddress;

	@Size(max = 300)
	private String permanentAddress;

	private String state;

	private String city;

	@Pattern(regexp = "^[0-9]{6}$", message = "Pincode must be 6 digits.")
	private String pincode;

	private String country;

}
