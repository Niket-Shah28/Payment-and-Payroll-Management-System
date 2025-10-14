package com.aurionpro.payrollsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aurionpro.payrollsystem.dto.employee.EmployeeRoleDto;
import com.aurionpro.payrollsystem.entity.employee.EmployeeRole;

@Repository
public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, Long>{
	@Query("SELECT new com.aurionpro.payrollsystem.dto.employee.EmployeeRoleDto("
		 + "r.employeeRoleId AS roleId, r.roleName AS role) "
		 + "FROM EmployeeRole r "
		 + "WHERE r.organization.organizationId = :organizationId AND r.isActive = TRUE")
	List<EmployeeRoleDto> getOrganizationActiveRoles(@Param("organizationId") Long organizationId);
}
