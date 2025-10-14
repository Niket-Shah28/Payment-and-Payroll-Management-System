package com.aurionpro.payrollsystem.service.organizationApplication;

import java.util.List;

import com.aurionpro.payrollsystem.dto.organizationApplication.OrganizationApplicationRequestDetailsDto;
import com.aurionpro.payrollsystem.dto.organizationApplication.OrganizationApplicationRequestDocumentsDto;
import com.aurionpro.payrollsystem.dto.organizationApplication.OrganizationApplicationRequestDto;
import com.aurionpro.payrollsystem.dto.organizationApplication.OrganizationRequestDocumentsResponseDto;
import com.aurionpro.payrollsystem.entity.employee.Status;

import jakarta.servlet.http.HttpServletResponse;

public interface OrganizationApplicationService {
	public Long addOrganizationApplication(OrganizationApplicationRequestDto dto);
	
	public void addOrganizationApplicationDocuments(OrganizationApplicationRequestDocumentsDto dto);

	public List<OrganizationApplicationRequestDetailsDto> getPendingRequests();
	
	public List<OrganizationRequestDocumentsResponseDto> getRequestDocuments(Long requestId);
	
	public void processOrganizationRequest(Long requestId, Status status);

	public 	void streamDocument(Long requestId, Long documentId, HttpServletResponse response, boolean isDownload);
	
}
