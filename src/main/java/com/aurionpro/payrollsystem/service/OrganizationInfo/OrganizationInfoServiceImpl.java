package com.aurionpro.payrollsystem.service.OrganizationInfo;



import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.aurionpro.payrollsystem.dto.OrganizationInfo.OrganizationDocumentDto;
import com.aurionpro.payrollsystem.dto.OrganizationInfo.OrganizationInfoDto;
import com.aurionpro.payrollsystem.entity.documents.Documents;
import com.aurionpro.payrollsystem.entity.organization.Organization;
import com.aurionpro.payrollsystem.repository.OrganizationDocumentRepository;
import com.aurionpro.payrollsystem.repository.OrganizationInfoRepository;

@Service
public class OrganizationInfoServiceImpl implements OrganizationInfoService {

    @Autowired
    private OrganizationInfoRepository organizationRepository;

    @Autowired
    private OrganizationDocumentRepository documentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<OrganizationInfoDto> getAllOrganizations(int pageNumber, int pageSize) {
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);
        Page<Organization> organizations = organizationRepository.findAllOrganizations(pageable);

        return organizations.map(org -> modelMapper.map(org, OrganizationInfoDto.class));
    }

    @Override
    public List<OrganizationDocumentDto> getDocumentsByOrganizationId(Long organizationId) {
        return documentRepository.findDocumentsByOrganizationId(organizationId);
    }

}
