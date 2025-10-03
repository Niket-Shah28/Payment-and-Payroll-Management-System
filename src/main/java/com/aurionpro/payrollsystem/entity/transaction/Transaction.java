package com.aurionpro.payrollsystem.entity.transaction;

import java.sql.Timestamp;

import com.aurionpro.payrollsystem.entity.employee.Employee;
import com.aurionpro.payrollsystem.entity.organization.Organization;
import com.aurionpro.payrollsystem.entity.vendor.Vendor;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "transaction")
public class Transaction {
	
	@Id
	@Column(name = "transaction_id", columnDefinition = "CHAR(36) NOT NULL DEFAULT (UUID())")
	private String transactionId;

	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "organization_id")
	private Organization organizationId;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "source_account_number", nullable = false)
	private String sourceAccountNumber;
	
	@Column(name = "destination_account_number", nullable = false)
	private String destinationAccountNumber;
	
	@Column(name = "reference_number")
	private String reference_number;
	
	@Column(name = "receiver_bank_name")
	private String receiverBankName;
	
	@Column(name = "receiver_ifsc_code")
	private String receiverIfscCode;
	
	@Column(name = "amount", columnDefinition = "DECIMAL(15, 2)")
	private Double amount;
	
	@Column(name = "receiver_holder_name")
	private String receiverHolderName;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "paymentMode", nullable = false)
	private PaymentMode paymentMode;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "paymentType", nullable = false)
	private PaymentType paymentType;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "transaction_status", nullable = false)
	private TransactionStatus transactionStatus;
	
	@Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdAt;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "employee_id")
	private Employee employeeId;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "vendor_id")
	private Vendor vendorId;
}
