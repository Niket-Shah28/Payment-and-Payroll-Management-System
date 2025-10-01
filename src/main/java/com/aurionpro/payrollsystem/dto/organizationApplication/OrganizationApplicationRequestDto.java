package com.aurionpro.payrollsystem.dto.organizationApplication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class OrganizationApplicationRequestDto {
	@NotBlank
	private String organizationName;
	@Pattern(regexp = "^[UL][0-9]{5}[A-Z]{2}[0-9]{4}[A-Z]{3}[0-9]{6}$", message = "Invalid CIN Format")
	private String cinNumber;
	
	@Pattern(regexp = "^[a-zA-Z0-9._%-]+@[a-zA-Z]+\\.[a-z,A-Z]{2, }$", message = "Invalid Email Format")
	private String email;
	
	@Pattern(regexp = "^[6-9]{1}[0-9]{9}$", message = "Invalid Phone Number Format")
	private String phoneNumber;
	
	@NotBlank
	private String address;
	
	@Pattern(regexp = "^[0-9]{5}$", message = "Invalid Nic Code Fromat")
	private String nicCode;
	
	@Pattern(regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[0-9A-Z]{1}Z[0-9A-Z]{1}$", message = "Invalid gstin format")
	private String gstin;
	
	@Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$", message = "Invalid PAN format")
	private String pan;
	
	@Pattern(regexp = "^[A-Z]{4}[0-9]{5}[A-Z]{1}$", message = "Invalid TAN format")
	private String tan;
}
