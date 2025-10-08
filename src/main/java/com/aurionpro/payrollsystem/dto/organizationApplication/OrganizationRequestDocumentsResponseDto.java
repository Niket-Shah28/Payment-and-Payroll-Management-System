package com.aurionpro.payrollsystem.dto.organizationApplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class OrganizationRequestDocumentsResponseDto {
	private String documentTypeName;
	private String cloudinaryUrl;
}
