package com.aurionpro.payrollsystem.entity.leave;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "leave_type")
public class LeaveType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "leave_type_id")
	private Long leaveTypeId;

	@Column(name = "leave_type_name", nullable = false)
	private String leaveTypeName;
	
	@Column(name = "leave_count")
	private Integer leaveCount;
	
	@Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT TRUE")
	private Boolean isActive;
	
	@Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdAt;
	
	@Column(name = "updated_at", columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Timestamp updatedAt;
}
