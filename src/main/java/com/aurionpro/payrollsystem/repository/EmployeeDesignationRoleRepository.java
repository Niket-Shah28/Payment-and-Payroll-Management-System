package com.aurionpro.payrollsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aurionpro.payrollsystem.entity.employee.Employee;
import com.aurionpro.payrollsystem.entity.employee.EmployeeDesignationRole;

@Repository
public interface EmployeeDesignationRoleRepository extends JpaRepository<EmployeeDesignationRole, Long>{
	
	Optional<EmployeeDesignationRole> findByEmployeeId(Employee employeeId);

}
