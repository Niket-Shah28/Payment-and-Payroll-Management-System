package com.aurionpro.payrollsystem.dto.employeeDocs;

import com.aurionpro.payrollsystem.entity.documents.DocumentRole;
import com.aurionpro.payrollsystem.entity.documents.FileFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class DocumentTypeDto {

	private Long documentTypeId;
	private String documentTypeName;
	private FileFormat fileFormat;
	private Integer maxSize;
	private DocumentRole role;
	private Boolean compulsory;

}
