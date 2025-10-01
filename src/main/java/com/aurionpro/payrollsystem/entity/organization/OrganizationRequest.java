package com.aurionpro.payrollsystem.entity.organization;

import java.sql.Timestamp;

import com.aurionpro.payrollsystem.entity.employee.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "organization_request")
public class OrganizationRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "request_id")
	private Long requestId;
	
	@Column(name = "organization_name", nullable = false)
	private String organizationName;
	
	@Column(name = "cin_number", nullable = false)
	private String cinNumber;
	
	@Column(name = "email", unique = true, nullable = false)
	private String email;
	
	@Column(name = "phone_number", unique = true, nullable = false)
	private String phoneNumber;
	
	@Column(name = "address", nullable = false)
	private String address;
	
	@Column(name = "nic_code", nullable = false)
	private String nicCode;
	
	@Column(name = "gstin", nullable = false, unique = true)
	private String gstin;
	
	@Column(name = "pan", nullable = false, unique = true)
	private String pan;
	
	@Column(name = "tan", nullable = false, unique = true)
	private String tan;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status = Status.PENDING;
	
	@Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdAt;
	
	@Column(name = "updated_at", columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Timestamp updatedAt;
}
