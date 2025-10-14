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
	
	//@NotBlank(message = "Address is mandatory.")
	@Size(max = 300)
	private String currentAddress;
	
	//@NotBlank(message = "Address is mandatory.")
	@Size(max = 300)
	private String permanentAddress;
	
	//@NotBlank(message = "State is mandatory.")
	private String state;

    //@NotBlank(message = "City is mandatory.")
	private String city;
    
    //@NotBlank(message = "Pincode is mandatory.")
    @Pattern(regexp = "^[0-9]{6}$", message = "Pincode must be 6 digits.")
	private String pincode;
	
    //@NotBlank(message = "Country is mandatory.")
	private String country;

}
