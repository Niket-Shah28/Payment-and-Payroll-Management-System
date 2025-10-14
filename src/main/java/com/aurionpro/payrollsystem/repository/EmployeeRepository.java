package com.aurionpro.payrollsystem.repository;

import java.util.Optional;	


import org.springframework.data.jpa.repository.JpaRepository;	
import org.springframework.stereotype.Repository;

import com.aurionpro.payrollsystem.entity.employee.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	
	Optional<Employee> findById(Long employeeId);

}
