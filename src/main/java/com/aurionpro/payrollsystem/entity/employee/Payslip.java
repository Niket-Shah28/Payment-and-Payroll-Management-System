package com.aurionpro.payrollsystem.entity.employee;

import java.sql.Timestamp;
import java.time.Month;

import org.hibernate.validator.constraints.URL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
@Table(name = "payslip")
public class Payslip {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payslip_id")
	private Long payslipId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "month", nullable = false)
	private Month month;
	
	@Column(name = "year", nullable = false)
	private Integer year;
	
	@Column(name = "employee_name", nullable = false)
	private String name;
	
	@Column(name = "department", nullable = false)
	private String department;
	
	@Column(name = "role", nullable = false)
	private String role;  //developer, hr, etc
	
	@Column(name = "business_unit", nullable = false)
	private String businessUnit;
	
	@Column(name = "account_number", nullable = false)
	private String accountNumber;
	
	@Column(name = "ifsc_code", nullable = false)
	private String ifscCode;
	
	@Column(name = "bank_name", nullable = false)
	private String bankName;
	
	@Column(name = "basic_salary", columnDefinition = "DECIMAL(15, 2)", nullable = false)
	private Double basicSalary;
	
	@Column(name = "house_rent_allowance", columnDefinition = "DECIMAL(15, 2)", nullable = false)
	private Double houseRentAllowance;
	
	@Column(name = "dearness_allowance", columnDefinition = "DECIMAL(15, 2)", nullable = false)
	private Double dearnessAllowance;
	
	@Column(name = "provident_fund", columnDefinition = "DECIMAL(15, 2)", nullable = false)
	private Double providentFund;
	
	@Column(name = "other_allowance", columnDefinition = "DECIMAL(15, 2)", nullable = false)
	private Double otherAllowance;
	
	@Column(name = "actual_salary", columnDefinition = "DECIMAL(15, 2)", nullable = false)
	private Double actualSalary;
	
	@Column(name = "final_salary", columnDefinition = "DECIMAL(15, 2)", nullable = false)
	private Double finalSalary;
	
	@Column(name = "created_at", updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdAt;
	
	@Column(name = "updated_at", updatable = false, insertable = false, columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Timestamp updatedAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;
}
