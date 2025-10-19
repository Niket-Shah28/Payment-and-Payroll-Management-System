package com.aurionpro.payrollsystem.dto.organizationtransaction;


import java.sql.Timestamp;	
import java.time.LocalDateTime;
import java.time.Month;

import com.aurionpro.payrollsystem.entity.transaction.PaymentMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import com.aurionpro.payrollsystem.entity.employee.Status;
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PaymentReqestDto {

	 private Long paymentRequestId;
	    private Double amount;
	    private String paymentFileUrl;
	    private String paymentRecipientType;
	    private String recipientAccountNumber;
	    private String recipientBankName;
	    private String recipientIfscCode;
	    private LocalDateTime scheduledTime;
	    private Boolean singlePayment;
	    private Status status;
	    private String organizationName;
	    private String recipientName;
	    private Timestamp createdAt;
	    private Month month;
	    private Integer year;
	    private PaymentMode paymentMode;
}
