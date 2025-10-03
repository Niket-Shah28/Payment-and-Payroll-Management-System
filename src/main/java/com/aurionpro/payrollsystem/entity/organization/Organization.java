package com.aurionpro.payrollsystem.entity.organization;

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
@Table(name = "organization")
public class Organization {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long organizationId;
	
	@Column(name = "organization_name", unique = true, nullable = false)
	private String organizationName;
	
	@Column(name = "cin_number", nullable = false)
	private String cinNumber;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;
	
	@Column(name = "address", nullable = false)
	private String address;
	
	@Column(name = "nicCode")
	private Integer nicCode;
	
	@Column(name = "gstin", nullable = false, unique = true)
	private String gstin;
	
	@Column(name = "pan", nullable = false, unique = true)
	private String pan;
	
	@Column(name = "tan", nullable = false, unique = true)
	private String tan;
	
	@Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdAt;
	
	@Column(name = "updated_at", columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Timestamp updatedAt;
	
	@Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT TRUE")
	private Boolean isActive;
}
