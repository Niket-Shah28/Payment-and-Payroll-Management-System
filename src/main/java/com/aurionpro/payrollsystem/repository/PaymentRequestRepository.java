package com.aurionpro.payrollsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurionpro.payrollsystem.entity.employee.Status;
import com.aurionpro.payrollsystem.entity.transaction.PaymentRequest;

public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, Long>{
	Optional<PaymentRequest> findByPaymentRequestIdAndStatus(Long requestId, Status status);
}
