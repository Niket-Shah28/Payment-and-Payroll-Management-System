package com.aurionpro.payrollsystem.service.employeeInterface;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurionpro.payrollsystem.dto.employeeDocs.DocumentDto;
import com.aurionpro.payrollsystem.dto.employeeDocs.DocumentTypeDto;
import com.aurionpro.payrollsystem.dto.employeeDocs.DocumentUploadDto;
import com.aurionpro.payrollsystem.entity.documents.DocumentType;
import com.aurionpro.payrollsystem.entity.documents.Documents;
import com.aurionpro.payrollsystem.entity.employee.Employee;
import com.aurionpro.payrollsystem.entity.organization.Organization;
import com.aurionpro.payrollsystem.repository.DocumentRepository;
import com.aurionpro.payrollsystem.repository.DocumentTypeRepository;
import com.aurionpro.payrollsystem.repository.EmployeeRepository;
import com.aurionpro.payrollsystem.repository.OrganizationRepository;
import com.aurionpro.payrollsystem.entity.documents.Documents;

@Service
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private DocumentTypeRepository documentTypeRepository;

//	@Autowired
//	private Cloudinary cloudinary;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private OrganizationRepository organizationRepository;

//	@Override
//	@Transactional
//	 public DocumentDto uploadDocument(Long employeeId, Long organizationId,  Long documentTypeId, MultipartFile file) {
//		
//
//		
//        try {
//            // Validate
//            if (file.isEmpty()) {
//                throw new IllegalArgumentException("File cannot be empty");
//            }
//
//            
//            
//            DocumentType documentType = documentTypeRepository.findById(documentTypeId)
//                    .orElseThrow(() -> new RuntimeException("Document type not found with id: " + documentTypeId));
//
//            // Validate format
//            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1);
//            FileFormat fileFormat = FileFormat.valueOf(extension.toLowerCase());
//
//            if (!documentType.getFileFormat().equals(fileFormat)) {
//                throw new IllegalArgumentException(
//                        "Invalid file format. Expected: " + documentType.getFileFormat());
//            }
//
//            // Upload to Cloudinary
//            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(),
//                    ObjectUtils.asMap("folder", "employee_documents/"));
//
//            String cloudUrl = uploadResult.get("secure_url").toString();
//
//            // Save to DB
//            Documents document = new Documents();
//            document.setCloudinaryUrl(cloudUrl);
//            document.setDocumentType(documentType);
//            document.setDocumentSize((int) file.getSize());
//            document.setFileFormat(fileFormat);
//            document.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//           
//            
//            Employee emp = employeeRepository.findById(employeeId)
//    		        .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));
//    		document.setEmployeeId(emp);
//    		
//    		Organization org = organizationRepository.findById(organizationId)
//    				.orElseThrow(() -> new RuntimeException("Organization not found with id: "+ organizationId));
//    		document.setOrganization(org);
//
////            Employee emp = new Employee();
////            emp.setEmployeeId(employeeId);
////            document.setEmployeeId(emp);
//
//            Documents saved = documentRepository.save(document);
//            return convertToDto(saved);
//
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to upload file to Cloudinary", e);
//        }
//    }

	@Override
	public List<DocumentDto> getDocumentsByEmployeeId(Long employeeId) {
		List<Documents> documents = documentRepository.findByEmployeeIdWithDetails(employeeId);
		return documents.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	@Override
	public DocumentDto getDocumentById(Long documentId) {
		Documents document = documentRepository.findByIdWithDetails(documentId)
				.orElseThrow(() -> new RuntimeException("Document not found with id: " + documentId));
		return convertToDto(document);
	}

	@Override
	public List<DocumentDto> getDocumentsByEmployeeIdAndDocumentType(Long employeeId, Long documentTypeId) {
		List<Documents> documents = documentRepository
				.findByEmployeeId_EmployeeIdAndDocumentType_DocumentTypeId(employeeId, documentTypeId);
		return documents.stream().map(this::convertToDto).collect(Collectors.toList());
	}

//	@Override
//	@Transactional
//	public void deleteDocument(Long documentId) {
//	    Documents doc = documentRepository.findById(documentId)
//	            .orElseThrow(() -> new RuntimeException("Document not found"));
//
//	    // Mark document as inactive instead of deleting it
//	    doc.setIsActive(0);
//	    doc.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
//
//	    try {
//	        // Optional: Delete from Cloudinary (only if you want to remove the actual file)
//	        String publicId = extractPublicIdFromUrl(doc.getCloudinaryUrl());
//	        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
//	    } catch (Exception e) {
//	        System.out.println("Warning: Could not delete file from Cloudinary -> " + e.getMessage());
//	    }
//
//	    // Save updated entity (soft delete)
//	    documentRepository.save(doc);
//	    
//	}
//	
//	
//	private String extractPublicIdFromUrl(String url) {
//        // Extract public_id between last '/' and '.' in Cloudinary URL
//        String[] parts = url.split("/");
//        String lastPart = parts[parts.length - 1];
//        return "employee_documents/" + lastPart.substring(0, lastPart.lastIndexOf('.'));
//    }
	

	@Override
	public List<DocumentTypeDto> getAllActiveDocumentTypes() {
		List<DocumentType> documentTypes = documentTypeRepository.findByIsActive(true);
		return documentTypes.stream().map(this::convertTypeToDto).collect(Collectors.toList());
	}

//	private DocumentDto convertToDto(Documents document) {
//		DocumentDto dto = new DocumentDto();
//		dto.setDocumentId(document.getDocumentId());
//		dto.setCloudinaryUrl(document.getCloudinaryUrl());
//		dto.setDocumentSize(document.getDocumentSize());
//		dto.setFileFormat(document.getFileFormat());
//		// dto.setCreatedAt(document.getCreatedAt());
//		// dto.setUpdatedAt(document.getUpdatedAt());
//
////		if (document.getEmployeeId() != null) {
////			dto.setEmployeeId(document.getEmployeeId().getEmployeeId());
////			dto.setEmployeeName(document.getEmployeeId().getFirstName() + " " + document.getEmployeeId().getLastName());
////		}
////
////		if (document.getOrganziation() != null) {
////			dto.setOrganizationId(document.getOrganziation().getOrganizationId());
////			dto.setOrganizationName(document.getOrganziation().getOrganizationName());
////		}
//		DocumentTypeDto typedto = new DocumentTypeDto();
//		if (document.getDocumentType() != null) {
//			typedto.setDocumentTypeId(document.getDocumentType().getDocumentTypeId());
//			typedto.setDocumentTypeName(document.getDocumentType().getDocumentTypeName());
//		}
//
//		return dto;
//	}

	private DocumentDto convertToDto(Documents document) {
        DocumentDto dto = new DocumentDto();
        dto.setDocumentId(document.getDocumentId());
        dto.setCloudinaryUrl(document.getCloudinaryUrl());
        dto.setDocumentTypeId(document.getDocumentType().getDocumentTypeId());
        dto.setDocumentSize(document.getDocumentSize());
        dto.setFileFormat(document.getFileFormat());
        return dto;
    }
	
	private DocumentTypeDto convertTypeToDto(DocumentType documentType) {
		DocumentTypeDto dto = new DocumentTypeDto();
		dto.setDocumentTypeId(documentType.getDocumentTypeId());
		dto.setDocumentTypeName(documentType.getDocumentTypeName());
		dto.setFileFormat(documentType.getFileFormat());
		dto.setMaxSize(documentType.getMaxSize());
		dto.setRole(documentType.getRole());
		dto.setCompulsory(documentType.getCompulsory());
		return dto;
	}
	
	@Override
	public DocumentDto saveDocument(DocumentUploadDto uploadDto, Long employeeId) {

	    // 1) Validate document type
	    DocumentType documentType = documentTypeRepository.findById(uploadDto.getDocumentTypeId())
	            .orElseThrow(() -> new IllegalArgumentException("Invalid Document Type ID: " + uploadDto.getDocumentTypeId()));

	    // 2) Fetch employee (from authentication)
	    Employee employee = employeeRepository.findById(employeeId)
	            .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + employeeId));

	    // 3) Get organization from employee
	    Organization organization = employee.getOrganization();
	    if (organization == null) {
	        throw new IllegalArgumentException("Employee does not belong to any organization");
	    }

	    // 4) Validate file size
	    uploadDto.validateFileSize();

	    // 5) Create Documents entity
	    Documents document = new Documents();
	    document.setDocumentType(documentType);
	    document.setCloudinaryUrl(uploadDto.getCloudinaryUrl());
	    document.setEmployeeId(employee);
	    document.setOrganization(organization);
	    document.setFileFormat(uploadDto.getFileFormat());
	    document.setDocumentSize(uploadDto.getDocumentSize());
	    document.setCreatedAt(new Timestamp(System.currentTimeMillis()));

	    // 6) Persist
	    Documents savedDoc = documentRepository.save(document);

	    // 7) Map to DTO
	    DocumentDto dto = new DocumentDto();
	    dto.setDocumentId(savedDoc.getDocumentId());
	    dto.setCloudinaryUrl(savedDoc.getCloudinaryUrl());
	    dto.setDocumentTypeId(savedDoc.getDocumentType().getDocumentTypeId());
	    dto.setDocumentSize(savedDoc.getDocumentSize());
	    dto.setFileFormat(savedDoc.getFileFormat());

	    return dto;
	}




//	private String formatFileSize(Integer sizeInBytes) {
//		if (sizeInBytes == null) {
//			return "Unknown";
//		}
//
//		double sizeInMB = sizeInBytes / (1024.0 * 1024.0);
//		if (sizeInMB >= 1.0) {
//			return String.format("%.2f MB", sizeInMB);
//		}
//
//		double sizeInKB = sizeInBytes / 1024.0;
//		return String.format("%.2f KB", sizeInKB);
//	}

}
