package com.aurionpro.payrollsystem.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aurionpro.payrollsystem.entity.leave.LeaveRequest;
import com.aurionpro.payrollsystem.entity.leave.LeaveStatus;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByEmployeeIdEmployeeId(Long employeeId); // note employeeId is Employee typed field

   
    List<LeaveRequest> findByManagerIdEmployeeId(Long managerId);

   
    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM LeaveRequest l " +
           "WHERE l.employeeId.employeeId = :employeeId AND l.leaveStatus = :status " +
           "AND :date BETWEEN l.startDate AND l.endDate")
    boolean existsByEmployeeAndDateWithStatus(@Param("employeeId") Long employeeId, @Param("date") LocalDate date, @Param("status") LeaveStatus status);
}
