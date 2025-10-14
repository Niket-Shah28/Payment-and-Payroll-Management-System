package com.aurionpro.payrollsystem.dto.payment;

import java.sql.Timestamp;

import org.hibernate.validator.constraints.URL;

import com.aurionpro.payrollsystem.entity.employee.Employee;
import com.aurionpro.payrollsystem.entity.employee.Status;
import com.aurionpro.payrollsystem.entity.organization.Organization;
import com.aurionpro.payrollsystem.entity.transaction.PaymentRecipientType;
import com.aurionpro.payrollsystem.entity.vendor.Vendor;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PaymentRequestDto {
	@NotBlank
	private PaymentRecipientType paymentRecipientType;
	@Min(1)
	private Double amount;
	@URL
	@Nullable
	private String paymentFileUrl;
	private Timestamp scheduledTime;
	@NotBlank
	private Organization organizationId;
	@NotBlank
	private Boolean singlePayment;
	private Employee employeeId;
	private Vendor vendorId;
	private String recipientAccountNumber;
	private String recipientBankName;
	private String recipientIfscCode;
	private Status status;
}
