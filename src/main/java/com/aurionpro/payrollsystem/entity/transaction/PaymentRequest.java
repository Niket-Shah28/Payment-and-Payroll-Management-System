package com.aurionpro.payrollsystem.entity.transaction;

import java.sql.Timestamp;

import org.hibernate.validator.constraints.URL;

import com.aurionpro.payrollsystem.entity.employee.Employee;
import com.aurionpro.payrollsystem.entity.employee.Status;
import com.aurionpro.payrollsystem.entity.organization.Organization;
import com.aurionpro.payrollsystem.entity.vendor.Vendor;

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
@Table(name = "payment_request")
public class PaymentRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long paymentRequestId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "paymentRecipientType")
	private PaymentRecipientType paymentRecipientType;
	
	@Column(name = "amount", nullable = false)
	private Double amount;
	
	@Column(name = "payment_file_url", nullable = false)
	@URL
	private String paymentFileUrl;
	
	@Column(name = "schedule_time", nullable = false)
	private Timestamp scheduledTime;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "organization_id", nullable = false)
	private Organization organizationId;
	
	@Column(name = "single_payment")
	private Boolean singlePayment;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "employee_id")
	private Employee employeeId;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "vendor_id")
	private Vendor vendorId;
	 
	@Column(name = "recipient_account_number")
	private String recipientAccountNumber;
	
	@Column(name = "recipient_bank_name")
	private String recipientBankName;
	
	@Column(name = "recipient_ifsc_code")
	private String recipientIfscCode;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private Status status;

}
