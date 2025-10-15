package com.aurionpro.payrollsystem.service.OrganizationInfo;



import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

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

import jakarta.servlet.http.HttpServletResponse;

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
    @Override
    public void streamEmployeeDocument(Long organizationId, Long documentId, HttpServletResponse response, boolean isDownload) {
        
        Documents document = documentRepository
            .findByOrganization_OrganizationIdAndDocumentId(organizationId, documentId)
            .orElseThrow(() -> new RuntimeException("Document not found"));

        String fileUrl = document.getCloudinaryUrl();
        String fileType = document.getFileFormat().name(); 

        try (InputStream inputStream = new URL(fileUrl).openStream();
             OutputStream outputStream = response.getOutputStream()) {

        
            if (fileType.equalsIgnoreCase("pdf")) {
                response.setContentType("application/pdf");
            } else if (fileType.equalsIgnoreCase("jpg") || fileType.equalsIgnoreCase("jpeg") || fileType.equalsIgnoreCase("png")) {
                response.setContentType("image/" + fileType.toLowerCase());
            } else {
                response.setContentType("application/octet-stream");
            }

           
            String dispositionType = isDownload ? "attachment" : "inline";
            response.setHeader("Content-Disposition", dispositionType + "; filename=document." + fileType.toLowerCase());
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, private");

        
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
