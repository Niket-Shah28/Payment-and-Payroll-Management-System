package com.aurionpro.payrollsystem.service.organizationtransaction;

import org.springframework.data.domain.Page;

import com.aurionpro.payrollsystem.dto.organizationtransaction.PaymentReqestDto;
import com.aurionpro.payrollsystem.entity.employee.Status;

public interface PaymentRequestService {
	public Page<PaymentReqestDto> getAllPaymentRequests(int pageNumber, int pageSize);
	public void updatePaymentStatus(Long paymentRequestId, Status statusStr);
}