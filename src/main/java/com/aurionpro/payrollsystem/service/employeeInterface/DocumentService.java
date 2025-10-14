package com.aurionpro.payrollsystem.service.employeeInterface;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.aurionpro.payrollsystem.dto.employeeDocs.DocumentDto;
import com.aurionpro.payrollsystem.dto.employeeDocs.DocumentTypeDto;

public interface DocumentService {

	DocumentDto uploadDocument(Long employeeId, Long organizationId, Long documentTypeId, MultipartFile file);


	List<DocumentDto> getDocumentsByEmployeeId(Long employeeId);

	DocumentDto getDocumentById(Long documentId);

	List<DocumentDto> getDocumentsByEmployeeIdAndDocumentType(Long employeeId, Long documentTypeId);

	//void deleteDocument(Long documentId);

	    List<DocumentTypeDto> getAllActiveDocumentTypes();


}
