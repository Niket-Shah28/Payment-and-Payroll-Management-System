package com.aurionpro.payrollsystem.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.payrollsystem.dto.organizationtransaction.PaymentReqestDto;
import com.aurionpro.payrollsystem.dto.organizationtransaction.UpdatePaymentStatusDto;
import com.aurionpro.payrollsystem.service.organizationtransaction.PaymentRequestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/banks/payments")
public class BankPaymentController {

    @Autowired
    private PaymentRequestService paymentRequestService;

    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/requests")
    public ResponseEntity<Page<PaymentReqestDto>> getAllPayments(
            @RequestParam int pageNumber,
            @RequestParam int pageSize) {

        return ResponseEntity.ok(paymentRequestService.getAllPaymentRequests(pageNumber, pageSize));
    }
    
    @PutMapping("/requests/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updatePaymentStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePaymentStatusDto dto) {

        paymentRequestService.updatePaymentStatus(id, dto.getStatus());
        return ResponseEntity.ok("Payment status updated successfully");
    }



}

