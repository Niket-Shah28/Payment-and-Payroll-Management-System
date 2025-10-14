package com.aurionpro.payrollsystem.dto.manageEmployeeProfile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class EmployeeAddressResponseDto {

	private String currentAddress;

	private String permanentAddress;

	private String state;

	private String city;

	private String pincode;

	private String country;

}
