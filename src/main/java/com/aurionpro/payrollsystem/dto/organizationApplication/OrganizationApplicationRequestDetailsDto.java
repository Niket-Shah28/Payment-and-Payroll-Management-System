package com.aurionpro.payrollsystem.dto.organizationApplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class OrganizationApplicationRequestDetailsDto {
	private Long requestId;
	private String organizationName;
	private String email;
	private String phoneNumber;
	private String address;
	private String cinNumber;
	private String nicCode;
	private String gstin;
	private String pan;
	private String tan;
}
