package com.aurionpro.payrollsystem.controller;


import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.payrollsystem.dto.organizationtransaction.TransactionDetailsDto;
import com.aurionpro.payrollsystem.service.organizationtransaction.BankTransactionService;

@RestController
@RequestMapping("/banks")
public class OrganizationTransactionController {

    @Autowired
    private BankTransactionService bankTransactionService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/transactions")
    public ResponseEntity<Page<TransactionDetailsDto>> getTransactions(
            @RequestParam(required = false) String entityName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {

        Page<TransactionDetailsDto> page = bankTransactionService
                .getFilteredTransactions(entityName, dateFrom, dateTo, pageNumber, pageSize);

        return ResponseEntity.ok(page);
    }
}

