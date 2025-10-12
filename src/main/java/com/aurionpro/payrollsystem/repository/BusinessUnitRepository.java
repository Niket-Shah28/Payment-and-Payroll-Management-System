package com.aurionpro.payrollsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aurionpro.payrollsystem.dto.employee.BusinessUnitDto;
import com.aurionpro.payrollsystem.entity.employee.BusinessUnit;

@Repository
public interface BusinessUnitRepository extends JpaRepository<BusinessUnit, Long> {
	@Query("SELECT new com.aurionpro.payrollsystem.dto.employee.BusinessUnitDto("
		 + "b.businessUnitId, b.businessUnitName) "
		 + "FROM BusinessUnit b "
		 + "WHERE b.organization.organizationId = :organizationId AND b.isActive = TRUE")
	List<BusinessUnitDto> getOrganizationActiveBusinessUnits(@Param("organizationId") Long organizationId);
}
