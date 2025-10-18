package com.aurionpro.payrollsystem.dto.vendor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class VendorResponseDto {
	private Long vendorId;
	private String name;
	private String email;
	private String phoneNumber;
	private String gstin;
	private String pan;
}
