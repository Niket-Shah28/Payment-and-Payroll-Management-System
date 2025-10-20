package com.aurionpro.payrollsystem.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aurionpro.payrollsystem.entity.employee.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	
	Optional<Employee> findById(Long employeeId);
	
	@Query("""
		       SELECT e FROM Employee e 
		       WHERE e.organization.organizationId = :organizationId 
		       AND (STR(e.employeeId) LIKE CONCAT('%', :searchTerm, '%') 
		       OR LOWER(e.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')))
		       """)
    Page<Employee> searchEmployees(@Param("searchTerm") String searchTerm, Pageable pageable, @Param("organizationId") Long organizationId);

}
