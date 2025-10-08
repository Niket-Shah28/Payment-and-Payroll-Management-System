package com.aurionpro.payrollsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aurionpro.payrollsystem.dto.employee.DepartmentDto;
import com.aurionpro.payrollsystem.entity.employee.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{
	@Query("SELECT new com.aurionpro.payrollsystem.dto.employee.DepartmentDto("
		 + "d.departmentId, d.departmentName) "
		 + "FROM Department d "
		 + "WHERE d.organization.organizationId = :organizationId AND d.isActive = TRUE")
	List<DepartmentDto> getOrganizationActiveDepartments(@Param("organizationId") Long organizationId);
}
