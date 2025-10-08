package com.aurionpro.payrollsystem.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aurionpro.payrollsystem.entity.employee.Status;
import com.aurionpro.payrollsystem.entity.transaction.PaymentRequest;

public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, Long> {

    Page<PaymentRequest> findAll(Pageable pageable);
}


