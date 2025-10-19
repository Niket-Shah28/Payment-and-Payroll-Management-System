package com.aurionpro.payrollsystem.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.payrollsystem.dto.OrganizationInfo.OrganizationDocumentDto;
import com.aurionpro.payrollsystem.dto.OrganizationInfo.OrganizationInfoDto;
import com.aurionpro.payrollsystem.service.OrganizationInfo.OrganizationInfoService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/banks/organizations")
@CrossOrigin(origins="http://localhost:4200")
public class OrganizationInfoController {

    @Autowired
    private OrganizationInfoService organizationService;


    @GetMapping("/info")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<OrganizationInfoDto>> getAllOrganizations(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "1") int pageSize) {

        Page<OrganizationInfoDto> organizations = organizationService.getAllOrganizations(pageNumber, pageSize);
        return ResponseEntity.ok(organizations);
    }


    @GetMapping("/{organizationId}/documents")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrganizationDocumentDto>> getDocumentsByOrganizationId(
            @PathVariable Long organizationId) {

        List<OrganizationDocumentDto> documents = organizationService.getDocumentsByOrganizationId(organizationId);
        return ResponseEntity.ok(documents);
    }
    
    @GetMapping("/{orgId}/documents/{docId}/view")
    @PreAuthorize("hasRole('ADMIN')")
    public void viewDocument(@PathVariable Long orgId,
                             @PathVariable Long docId,
                             HttpServletResponse response) {
    	organizationService.streamEmployeeDocument(orgId, docId, response, false);
    }

    @GetMapping("/{orgId}/documents/{docId}/download")
    @PreAuthorize("hasRole('ADMIN')")
    public void downloadDocument(@PathVariable Long orgId,
                                 @PathVariable Long docId,
                                 HttpServletResponse response) {
    	organizationService.streamEmployeeDocument(orgId, docId, response, true);
    }

}
