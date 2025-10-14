package com.aurionpro.payrollsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aurionpro.payrollsystem.entity.employee.Employee;
import com.aurionpro.payrollsystem.entity.organization.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long>{
	
	Optional<Organization> findById(Long organizationId);

}
