package com.aurionpro.payrollsystem.service.organizationtransaction;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import com.aurionpro.payrollsystem.dto.organizationtransaction.TransactionDetailsDto;

public interface BankTransactionService {
    Page<TransactionDetailsDto> getFilteredTransactions(
            String entityName,
            LocalDateTime dateFrom,
            LocalDateTime dateTo,
            int pageNumber,
            int pageSize
    );
}

