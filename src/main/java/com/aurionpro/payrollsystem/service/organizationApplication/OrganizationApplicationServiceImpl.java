package com.aurionpro.payrollsystem.service.organizationApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import com.aurionpro.payrollsystem.dto.organizationApplication.OrganizationApplicationRequestDetailsDto;
import com.aurionpro.payrollsystem.dto.organizationApplication.OrganizationApplicationRequestDocumentsDto;
import com.aurionpro.payrollsystem.dto.organizationApplication.OrganizationApplicationRequestDto;
import com.aurionpro.payrollsystem.exception.OrganizationApplicationRequestException;
import com.aurionpro.payrollsystem.repository.OrganizationApplicationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class OrganizationApplicationServiceImpl implements OrganizationApplicationService{

	@Autowired
	private OrganizationApplicationRepository organizationApplicationRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Override
	public Long addOrganizationApplication(OrganizationApplicationRequestDto dto) {
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("add_organization_application_request");

        // Input params
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("p_organization_name", dto.getOrganizationName());
        inParams.put("p_cin_number", dto.getCinNumber());
        inParams.put("p_email", dto.getEmail());
        inParams.put("p_phone_number", dto.getPhoneNumber());
        inParams.put("p_address", dto.getAddress());
        inParams.put("p_nic_code", dto.getNicCode());
        inParams.put("p_gstin", dto.getGstin());
        inParams.put("p_pan", dto.getPan());
        inParams.put("p_tan", dto.getTan());

        Map<String, Object> outParams = jdbcCall.execute(inParams);

        Long requestId = (Long) outParams.get("o_request_id");
        Boolean success = (Boolean) outParams.get("o_success");
        String message = (String) outParams.get("o_message");
		
		if(!success) {
			throw new OrganizationApplicationRequestException(message, HttpStatus.CONFLICT);
		}
		
		return requestId;
	}

	@Override
	public void addOrganizationApplicationDocuments(OrganizationApplicationRequestDocumentsDto dto) {
		
		Long requestId = dto.getRequestId();
		String documentsDataJson="";
		try {
			documentsDataJson = objectMapper.writeValueAsString(dto.getDocuments());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
		        .withProcedureName("add_organization_request_documents");

		Map<String, Object> inParams = new HashMap<>();
		inParams.put("p_request_id", requestId);
		inParams.put("p_documents_data_json", documentsDataJson); 

		Map<String, Object> outParams = jdbcCall.execute(inParams);

		Boolean success = (Boolean) outParams.get("o_success");
		String message = (String) outParams.get("o_message");
		
		if(!success) {
			throw new OrganizationApplicationRequestException(message, HttpStatus.NOT_FOUND);
		}
		return;
	}

	@Override
	public List<OrganizationApplicationRequestDetailsDto> getPendingRequests() {
		return organizationApplicationRepository.getPendingRequests();
	}
}
