package com.aurionpro.payrollsystem.service.organizationApplication;

import java.util.List;

import com.aurionpro.payrollsystem.dto.organizationApplication.OrganizationApplicationRequestDetailsDto;
import com.aurionpro.payrollsystem.dto.organizationApplication.OrganizationApplicationRequestDocumentsDto;
import com.aurionpro.payrollsystem.dto.organizationApplication.OrganizationApplicationRequestDto;

public interface OrganizationApplicationService {
	public Long addOrganizationApplication(OrganizationApplicationRequestDto dto);
	
	public void addOrganizationApplicationDocuments(OrganizationApplicationRequestDocumentsDto dto);

	public List<OrganizationApplicationRequestDetailsDto> getPendingRequests();
}
