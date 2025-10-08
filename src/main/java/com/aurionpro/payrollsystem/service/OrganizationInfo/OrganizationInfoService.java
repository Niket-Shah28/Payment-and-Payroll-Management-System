package com.aurionpro.payrollsystem.service.OrganizationInfo;




import org.springframework.data.domain.Page;

import com.aurionpro.payrollsystem.dto.OrganizationInfo.OrganizationDocumentDto;
import com.aurionpro.payrollsystem.dto.OrganizationInfo.OrganizationInfoDto;

import java.util.List;

public interface OrganizationInfoService {

    Page<OrganizationInfoDto> getAllOrganizations(int pageNumber, int pageSize);

    List<OrganizationDocumentDto> getDocumentsByOrganizationId(Long organizationId);
}
