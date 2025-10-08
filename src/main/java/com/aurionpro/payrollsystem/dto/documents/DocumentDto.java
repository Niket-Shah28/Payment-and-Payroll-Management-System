package com.aurionpro.payrollsystem.dto.documents;

import org.hibernate.validator.constraints.URL;

import com.aurionpro.payrollsystem.entity.documents.FileFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class DocumentDto {
	@NotNull
	private Long documentTypeId;
	
	@NotBlank
	@URL
	private String cloudinaryUrl;
	
	@NotNull
	@Min(1)
	private Integer documentSize;
	
	@NotNull
	private FileFormat fileFormat;
}
