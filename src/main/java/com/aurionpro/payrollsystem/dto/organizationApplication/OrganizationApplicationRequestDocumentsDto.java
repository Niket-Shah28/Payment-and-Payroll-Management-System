package com.aurionpro.payrollsystem.dto.organizationApplication;

import java.util.List;

import com.aurionpro.payrollsystem.dto.documents.DocumentDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class OrganizationApplicationRequestDocumentsDto {
	@NotNull
	private Long requestId;
	
	@Valid
	private List<DocumentDto> documents;
}
