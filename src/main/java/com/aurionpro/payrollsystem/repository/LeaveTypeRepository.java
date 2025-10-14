package com.aurionpro.payrollsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aurionpro.payrollsystem.entity.leave.LeaveType;

@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {
	Optional<LeaveType> findById (Long leaveType);
	
}