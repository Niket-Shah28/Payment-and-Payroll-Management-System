package com.aurionpro.payrollsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aurionpro.payrollsystem.entity.documents.DocumentType;
import com.aurionpro.payrollsystem.entity.documents.Documents;

@Repository
public interface DocumentRepository extends JpaRepository<Documents, Long> {

	List<Documents> findByOrganization_OrganizationId(Long organizationId);
	List<Documents> findByEmployeeId_EmployeeId(Long employeeId);


	
	List<Documents> findByEmployeeId_EmployeeIdOrderByCreatedAtDesc(Long employeeId);


	
	List<Documents> findByEmployeeId_EmployeeIdAndDocumentType_DocumentTypeId(Long employeeId, Long documentTypeId);

	
	@Query("SELECT d FROM Documents d " + "LEFT JOIN FETCH d.employeeId " + "LEFT JOIN FETCH d.organization "
			+ "LEFT JOIN FETCH d.documentType " + "WHERE d.employeeId.employeeId = :employeeId "
			+ "ORDER BY d.createdAt DESC")
	List<Documents> findByEmployeeIdWithDetails(@Param("employeeId") Long employeeId);

	
	@Query("SELECT d FROM Documents d " + "LEFT JOIN FETCH d.employeeId " + "LEFT JOIN FETCH d.organization "
			+ "LEFT JOIN FETCH d.documentType " + "WHERE d.documentId = :documentId")
	Optional<Documents> findByIdWithDetails(@Param("documentId") Long documentId);

	
	Long countByEmployeeId_EmployeeId(Long employeeId);



}
