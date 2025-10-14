package com.aurionpro.payrollsystem.repository;

import java.util.Optional;		

import org.springframework.data.jpa.repository.JpaRepository;	
import org.springframework.stereotype.Repository;

import com.aurionpro.payrollsystem.entity.employee.Employee;
import com.aurionpro.payrollsystem.entity.employee.EmployeeAddress;

@Repository
public interface EmployeeAddressRepository extends JpaRepository<EmployeeAddress, Long>{
	
	Optional<EmployeeAddress> findByEmployeeId(Employee employee);

}      
