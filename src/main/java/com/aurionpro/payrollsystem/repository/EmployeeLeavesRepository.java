package com.aurionpro.payrollsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aurionpro.payrollsystem.entity.employee.Employee;
import com.aurionpro.payrollsystem.entity.leave.EmployeeLeaves;
import com.aurionpro.payrollsystem.entity.leave.LeaveType;

@Repository
public interface EmployeeLeavesRepository extends JpaRepository<EmployeeLeaves, Long> {
    Optional<EmployeeLeaves> findByEmployeeAndLeaveType(Employee employee, LeaveType leaveType);
    Optional<EmployeeLeaves> findByEmployeeEmployeeIdAndLeaveTypeLeaveTypeId(Long employeeId, Long leaveTypeId);
}
