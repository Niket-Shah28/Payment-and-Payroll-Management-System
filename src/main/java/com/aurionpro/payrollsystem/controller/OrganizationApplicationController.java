package com.aurionpro.payrollsystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.payrollsystem.dto.organizationApplication.OrganizationApplicationRequestDetailsDto;
import com.aurionpro.payrollsystem.dto.organizationApplication.OrganizationApplicationRequestDocumentsDto;
import com.aurionpro.payrollsystem.dto.organizationApplication.OrganizationApplicationRequestDto;
import com.aurionpro.payrollsystem.dto.organizationApplication.OrganizationRequestDocumentsResponseDto;
import com.aurionpro.payrollsystem.entity.employee.Status;
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
	
	@GetMapping("/{requestId}/documents")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<OrganizationRequestDocumentsResponseDto>> getOrganizationRequestDocuments(@PathVariable Long requestId){
		return new ResponseEntity<>(organizationApplicationService.getRequestDocuments(requestId), HttpStatus.OK);
	}
	
	@PutMapping("/{requestId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<OrganizationRequestDocumentsResponseDto>> processOrganizationRequest(@PathVariable Long requestId, @RequestParam(name="status") Status status){
		organizationApplicationService.processOrganizationRequest(requestId, status);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
