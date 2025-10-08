package com.aurionpro.payrollsystem.entity.bankAccount;

import java.sql.Timestamp;

import com.aurionpro.payrollsystem.entity.organization.Organization;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "organization_bank_account")
public class OrganizationBankAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long accountId;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "organization_id", nullable = false)
	private Organization organization;
	
	@Column(name = "account_number", unique = true, nullable = false)
	private String accountNumber;
	
	@Column(name = "balance", columnDefinition = "DECIMAL(15, 2)")
	private Double balance;
	
	@Column(name = "ifsc_code", nullable = false)
	private String ifscCode;
	
	@Column(name = "bank_name", nullable = false)
	private String bankName;
	
	@Column(name = "account_holder_name", nullable = false)
	private String accountHolderName;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "accountType", nullable = false)
	private AccountType accountType;
	
	@Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT TRUE")
	private Boolean isActive;
	
	@Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdAt;
	
	@Column(name = "updated_at", columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Timestamp updatedAt;
}
