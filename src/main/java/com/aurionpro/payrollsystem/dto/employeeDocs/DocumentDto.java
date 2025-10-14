package com.aurionpro.payrollsystem.dto.employeeDocs;

import com.aurionpro.payrollsystem.entity.documents.FileFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class DocumentDto {

	private Long documentId;
	
	private String cloudinaryUrl;

	private Long documentTypeId;

	private Integer documentSize; // Size in bytes

	private FileFormat fileFormat;

}
