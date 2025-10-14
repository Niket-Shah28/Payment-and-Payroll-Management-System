package com.aurionpro.payrollsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aurionpro.payrollsystem.entity.documents.DocumentRole;
import com.aurionpro.payrollsystem.entity.documents.DocumentType;
import com.aurionpro.payrollsystem.entity.documents.Documents;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long>{

	List<DocumentType> findByIsActive(Boolean isActive);

	List<DocumentType> findByRole(DocumentRole role);

	List<DocumentType> findByRoleAndIsActive(DocumentRole role, Boolean isActive);

	List<DocumentType> findByCompulsory(Boolean compulsory);

	Optional<DocumentType> findByDocumentTypeName(String documentTypeName);

//	Optional<Documents> findByDocumentId(Documents document);

}
