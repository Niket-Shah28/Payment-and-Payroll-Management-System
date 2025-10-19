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
	private PaymentRecipientType paymentRecipientType;
	private Double amount;
	private String paymentFileUrl;
	private Timestamp scheduledTime;
	private Boolean singlePayment;
	private String recipientAccountNumber;
	private String recipientAccountHolderName;
	private String recipientBankName;
	private String recipientIfscCode;
	private Status status;
}
