package com.aurionpro.payrollsystem.entity.bankAccount;

import java.sql.Timestamp;

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
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_details")
public class BankAccountDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id")
	private Long accountId;

	@Column(name = "account_number", unique = true, nullable = false)
	private String accountNumber;
	
	@Column(name = "bank_name", nullable = false)
	private String bankName;
	
	@Column(name = "ifsc_code", nullable = false)
	private String ifscCode;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "accountType", nullable = false)
	private AccountType accountType;
	
	@Column(name = "account_holder_name", nullable = false)
	private String accountHolderName;
	
	@Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT TRUE")
	private Boolean isActive;
	
	@Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdAt;
	
	@Column(name = "updated_at", columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Timestamp updatedAt;
}
