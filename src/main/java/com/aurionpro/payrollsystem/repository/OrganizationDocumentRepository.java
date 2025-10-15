package com.aurionpro.payrollsystem.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aurionpro.payrollsystem.dto.OrganizationInfo.OrganizationDocumentDto;
import com.aurionpro.payrollsystem.entity.documents.Documents;

@Repository
public interface OrganizationDocumentRepository extends JpaRepository<Documents, Long> {

    @Query(value = """
        SELECT d.document_id AS documentId,
               d.created_at AS createdAt,
               dt.document_type_name AS documentTypeName
        FROM documents d
        LEFT JOIN document_type dt ON d.document_type_id = dt.document_type_id
        WHERE d.organization_id = :organizationId
    """, nativeQuery = true)
    List<OrganizationDocumentDto> findDocumentsByOrganizationId(@Param("organizationId") Long organizationId);
    
    Optional<Documents> findByOrganization_OrganizationIdAndDocumentId(Long organizationId, Long documentId);
}


