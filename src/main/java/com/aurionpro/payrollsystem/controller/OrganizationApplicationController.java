package com.aurionpro.payrollsystem.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.payrollsystem.dto.organizationApplication.OrganizationApplicationRequestDto;
import com.aurionpro.payrollsystem.service.organizationApplication.OrganizationApplicationService;

@RestController
@RequestMapping("/organization/requests")
public class OrganizationApplicationController {
	@Autowired
	private OrganizationApplicationService organizationApplicationService;
	
	@PostMapping("")
	public ResponseEntity<Map<String, Long>> organizationApplicationRequest(OrganizationApplicationRequestDto dto){
		Long requestId = organizationApplicationService.addOrganizationApplication(dto);
		return new ResponseEntity<>(Map.of("requestId", requestId), HttpStatus.OK);
	}

}
