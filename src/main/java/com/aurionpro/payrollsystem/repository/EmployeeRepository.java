package com.aurionpro.payrollsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurionpro.payrollsystem.entity.employee.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
