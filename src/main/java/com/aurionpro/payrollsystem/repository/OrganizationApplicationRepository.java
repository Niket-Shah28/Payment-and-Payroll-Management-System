package com.aurionpro.payrollsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aurionpro.payrollsystem.entity.organization.OrganizationRequest;

@Repository
public interface OrganizationApplicationRepository extends JpaRepository<OrganizationRequest, Long> {

}
