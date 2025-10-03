package com.aurionpro.payrollsystem.entity.employee;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee_designation_history")
public class EmployeeDesignationHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "designation_history_id")
	private Long designationHistoryId;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "employee_designation_role_id", nullable = false)
	private EmployeeDesignationRole employeeDesignationRoleId;
	
	@Column(name = "grade")
	private Integer grade;
	
	@Column(name = "start_time", updatable = false)
	private LocalDate startTime;
	
	@Column(name = "end_time", updatable = false)
	private LocalDate endTime;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "employee_role_id", nullable = false)
	private EmployeeRole employeeRoleId;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "business_unit_id", nullable = false)
	private BusinessUnit businessUnitId;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employeeId;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "department_id", nullable = false)
	private Department departmentId;
}
