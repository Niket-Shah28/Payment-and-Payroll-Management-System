package com.aurionpro.payrollsystem.dto.organizationApplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class OrganizationRequestDocumentsResponseDto {
	  private Long requestDocumentId;
	    private String documentTypeName;
	    private String cloudinaryUrl;
}
