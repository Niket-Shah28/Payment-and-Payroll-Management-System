package com.aurionpro.payrollsystem.entity.attendance;

import com.aurionpro.payrollsystem.entity.employee.Employee;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee_attendance")
public class Attendance {
	
	@EmbeddedId
    private AttendanceId attendanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "attendanceStatus", nullable = false)
	private AttendanceStatus attendanceStatus;

}
