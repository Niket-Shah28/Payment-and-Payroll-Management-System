package com.aurionpro.payrollsystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.payrollsystem.dto.organizationApplication.OrganizationApplicationRequestDetailsDto;
import com.aurionpro.payrollsystem.dto.organizationApplication.OrganizationApplicationRequestDocumentsDto;
import com.aurionpro.payrollsystem.dto.organizationApplication.OrganizationApplicationRequestDto;
import com.aurionpro.payrollsystem.service.organizationApplication.OrganizationApplicationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/organization/requests")
public class OrganizationApplicationController {
	@Autowired
	private OrganizationApplicationService organizationApplicationService;
	
	@PostMapping
	public ResponseEntity<Map<String, Long>> organizationApplicationRequest(@RequestBody @Valid OrganizationApplicationRequestDto dto){
		Long requestId = organizationApplicationService.addOrganizationApplication(dto);
		return new ResponseEntity<>(Map.of("requestId", requestId), HttpStatus.OK);
	}
	
	@PostMapping("/documents")
	public ResponseEntity<Void> organizationApplicationRequestDocuments(@RequestBody @Valid OrganizationApplicationRequestDocumentsDto dto){
		organizationApplicationService.addOrganizationApplicationDocuments(dto);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping("/pending")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<OrganizationApplicationRequestDetailsDto>> getPendingRequests(){
		return new ResponseEntity<>(organizationApplicationService.getPendingRequests(), HttpStatus.OK);
	}
}
