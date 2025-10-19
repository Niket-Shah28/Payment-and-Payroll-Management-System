package com.aurionpro.payrollsystem.service.organizationApplication;

import java.io.IOException;	
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import com.aurionpro.payrollsystem.dto.employee.EmailLoginInfoDto;
import com.aurionpro.payrollsystem.dto.organizationApplication.OrganizationApplicationRequestDetailsDto;
import com.aurionpro.payrollsystem.dto.organizationApplication.OrganizationApplicationRequestDocumentsDto;
import com.aurionpro.payrollsystem.dto.organizationApplication.OrganizationApplicationRequestDto;
import com.aurionpro.payrollsystem.dto.organizationApplication.OrganizationRequestDocumentsResponseDto;
import com.aurionpro.payrollsystem.entity.employee.Status;
import com.aurionpro.payrollsystem.entity.organization.OrganizationRequestDocuments;
import com.aurionpro.payrollsystem.exception.OrganizationApplicationRequestException;
import com.aurionpro.payrollsystem.repository.OrganizationApplicationDocuments;
import com.aurionpro.payrollsystem.repository.OrganizationApplicationRepository;
import com.aurionpro.payrollsystem.service.email.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;


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
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private OrganizationApplicationDocuments organizationApplicationDocuments;
	
	@Override
	public Long addOrganizationApplication(OrganizationApplicationRequestDto dto) {
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("add_organization_application_request");

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

	@Override
	public List<OrganizationRequestDocumentsResponseDto> getRequestDocuments(Long requestId) {
		System.out.println("Hello");
		return organizationApplicationRepository.getDocumentsList(requestId);
	}

	@Override
	public void processOrganizationRequest(Long requestId, Status status) {
		
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("process_organization_request");
		
		Map<String, Object> inParams = new HashMap<>();
        inParams.put("p_request_id", requestId);
        inParams.put("p_status", status.toString());
        
        Map<String, Object> outParams = jdbcCall.execute(inParams);
        Boolean success = (Boolean) outParams.get("o_success");
        String message = (String) outParams.get("o_message");
        String referenceId = (String) outParams.get("o_reference_id");
		String organizationEmail = (String) outParams.get("o_organization_email");
		String organizationName = (String) outParams.get("o_organization_name");
        
        System.out.println(referenceId);
        
        if(!success) {
			throw new OrganizationApplicationRequestException(message, HttpStatus.NOT_FOUND);
		}
        
        emailService.sendEmail("joining_email_template.html", new EmailLoginInfoDto(referenceId, organizationEmail, organizationName), "Welcome To Our Payroll System");
		return;
	}
	
	@Override
	public void streamDocument(Long requestId, Long documentId, HttpServletResponse response, boolean isDownload) {
	    OrganizationRequestDocuments document = organizationApplicationDocuments
	            .findByRequest_RequestIdAndRequestDocumentId(requestId, documentId)
	            .orElseThrow(() -> new OrganizationApplicationRequestException("Document not found", HttpStatus.NOT_FOUND));
 
	    String fileUrl = document.getCloudinaryUrl();
	    String fileType = document.getFileFormat().name();
 
	    try (InputStream inputStream = new URL(fileUrl).openStream();
	         OutputStream outputStream = response.getOutputStream()) {
 
	      
	        switch (fileType.toLowerCase()) {
	            case "pdf" -> response.setContentType("application/pdf");
	            case "jpg", "jpeg", "png" -> response.setContentType("image/" + fileType.toLowerCase());
	            default -> response.setContentType("application/octet-stream");
	        }
 
	        System.out.println("Hello2");
	        String fileName = "document_" + documentId + "." + fileType.toLowerCase();
	        if (isDownload) {
	            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
	        } else {
	            response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
	        }
 
	        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, private");
 
	        System.out.println("Hello3");
	        byte[] buffer = new byte[8192];
	        int bytesRead;
	        while ((bytesRead = inputStream.read(buffer)) != -1) {
	            outputStream.write(buffer, 0, bytesRead);
	        }
 
	        outputStream.flush();
 
	    } catch (IOException e) {
	        throw new RuntimeException("Error streaming document", e);
	    }
	}


	
	
}
