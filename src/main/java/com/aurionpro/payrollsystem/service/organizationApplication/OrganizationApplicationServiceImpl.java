package com.aurionpro.payrollsystem.service.organizationApplication;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurionpro.payrollsystem.dto.organizationApplication.OrganizationApplicationRequestDto;
import com.aurionpro.payrollsystem.entity.organization.OrganizationRequest;
import com.aurionpro.payrollsystem.repository.OrganizationApplicationRepository;

@Service
public class OrganizationApplicationServiceImpl implements OrganizationApplicationService{

	@Autowired
	private OrganizationApplicationRepository organizationApplicationRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public Long addOrganizationApplication(OrganizationApplicationRequestDto dto) {
		OrganizationRequest request = mapper.map(dto, OrganizationRequest.class);
		request = organizationApplicationRepository.save(request);
		return request.getRequestId();
	}

}
