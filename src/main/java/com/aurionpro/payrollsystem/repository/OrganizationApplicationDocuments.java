package com.aurionpro.payrollsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aurionpro.payrollsystem.entity.organization.OrganizationRequestDocuments;

@Repository
public interface OrganizationApplicationDocuments extends JpaRepository<OrganizationRequestDocuments, Long> {
	Optional<OrganizationRequestDocuments> findByRequest_RequestIdAndRequestDocumentId(Long requestId, Long documentId);

	
}
