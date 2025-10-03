package com.aurionpro.payrollsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aurionpro.payrollsystem.dto.organizationApplication.OrganizationApplicationRequestDetailsDto;
import com.aurionpro.payrollsystem.entity.organization.OrganizationRequest;

@Repository
public interface OrganizationApplicationRepository extends JpaRepository<OrganizationRequest, Long> {
	
	@Query("SELECT new com.aurionpro.payrollsystem.dto.organizationApplication.OrganizationApplicationRequestDetailsDto("
			+ "r.requestId, r.organizationName, r.email, r.phoneNumber, r.address, "
		 + "r.cinNumber, r.nicCode, r.gstin, r.pan, r.tan) "
		 + "FROM OrganizationRequest r "
		 + "WHERE r.status = 'PENDING'")
	List<OrganizationApplicationRequestDetailsDto> getPendingRequests();
}
