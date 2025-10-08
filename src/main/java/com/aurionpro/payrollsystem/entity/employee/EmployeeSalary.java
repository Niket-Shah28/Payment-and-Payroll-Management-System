package com.aurionpro.payrollsystem.entity.employee;

import java.sql.Timestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee_salary")
public class EmployeeSalary {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "salary_id")
	private Long salaryId;
	
	@Column(name = "basic_salary", columnDefinition = "DECIMAL(15, 2)")
	private Double basicSalary;
	
	@Column(name = "house_rent_allowance", columnDefinition = "DECIMAL(15, 2)")
	private Double houseRentAllowance;
	
	@Column(name = "dearness_allowance", columnDefinition = "DECIMAL(15, 2)")
	private Double dearnessAllowance;
	
	@Column(name = "provident_fund", columnDefinition = "DECIMAL(15, 2)")
	private Double providentFund;
	
	@Column(name = "other_allowance", columnDefinition = "DECIMAL(15, 2)")
	private Double otherAllowance;
	
	@Column(name = "final_salary", columnDefinition = "DECIMAL(15, 2)")
	private Double finalSalary;
	
	@Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT TRUE")
	private Boolean isActive;
	
	@Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdAt;
	
	@Column(name = "updated_at", columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Timestamp updatedAt;
	
	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employeeId;
}
