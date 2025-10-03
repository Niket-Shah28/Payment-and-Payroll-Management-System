package com.aurionpro.payrollsystem.entity.employee;

import java.sql.Timestamp;

import org.hibernate.validator.constraints.URL;

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
@Table(name = "payslip")
public class Payslip {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payslip_id")
	private Long payslipId;
	
	@Column(name = "month", columnDefinition = "INTEGER CHECK (month >= 1 AND month <= 12)")
	private Integer month;
	
	@Column(name = "year", nullable = false)
	private Integer year;
	
	@Column(name = "payslip_url", nullable = false)
	@URL
	private String payslipUrl;
	
	@Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdAt;
	
	@Column(name = "updated_at", columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Timestamp updatedAt;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employeeId;
}
