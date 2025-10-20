package com.aurionpro.payrollsystem.dto.payment;

import java.sql.Timestamp;
import java.time.Month;

import com.aurionpro.payrollsystem.entity.employee.Status;
import com.aurionpro.payrollsystem.entity.transaction.PaymentRecipientType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PaymentRequestDto {
	private PaymentRecipientType paymentRecipientType;
	private Double amount;
	private Integer year;
	private Month month;
	private String paymentFileUrl;
	private Timestamp scheduledTime;
	private Boolean singlePayment;
	private String recipientAccountNumber;
	private String recipientAccountHolderName;
	private String recipientBankName;
	private String recipientIfscCode;
	private Status status;
}
