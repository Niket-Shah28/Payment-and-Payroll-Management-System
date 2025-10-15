package com.aurionpro.payrollsystem.service.OrganizationInfo;




import java.util.List;

import org.springframework.data.domain.Page;

import com.aurionpro.payrollsystem.dto.OrganizationInfo.OrganizationDocumentDto;
import com.aurionpro.payrollsystem.dto.OrganizationInfo.OrganizationInfoDto;

import jakarta.servlet.http.HttpServletResponse;

public interface OrganizationInfoService {

    Page<OrganizationInfoDto> getAllOrganizations(int pageNumber, int pageSize);

    List<OrganizationDocumentDto> getDocumentsByOrganizationId(Long organizationId);
    public void streamEmployeeDocument(Long organizationId, Long documentId, HttpServletResponse response, boolean isDownload);

}
