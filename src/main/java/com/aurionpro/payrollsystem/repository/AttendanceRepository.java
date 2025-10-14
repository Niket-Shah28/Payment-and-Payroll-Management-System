package com.aurionpro.payrollsystem.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aurionpro.payrollsystem.entity.attendance.Attendance;
import com.aurionpro.payrollsystem.entity.attendance.AttendanceId;
import com.aurionpro.payrollsystem.entity.attendance.AttendanceStatus;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, AttendanceId> {
    List<Attendance> findByAttendanceIdEmployeeIdAndAttendanceIdDateBetween(Long employeeId, LocalDate from, LocalDate to);
    List<Attendance> findByAttendanceIdEmployeeId(Long employeeId);
    boolean existsByAttendanceIdEmployeeIdAndAttendanceIdDate(Long employeeId, LocalDate date);
	
    long countByAttendanceIdEmployeeIdAndAttendanceStatusAndAttendanceIdDateBetween(Long employeeId,
			AttendanceStatus present, LocalDate start, LocalDate end);
}
