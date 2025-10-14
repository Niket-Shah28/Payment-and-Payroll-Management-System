package com.aurionpro.payrollsystem.dto.organizationtransaction;


import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TransactionDetailsDto {
    private String transactionId;
    private Double amount;
    private Timestamp  createdAt;
    private String description;
    private String destinationAccountNumber;
    private String paymentMode;     // IMPS, NEFT, RTGS
    private String paymentType;     // CREDIT, DEBIT
    private String receiverBankName;
    private String receiverHolderName;
    private String receiverIfscCode;
    private String reference_number;
    private String sourceAccountNumber;
    private String transactionStatus;
 
}
