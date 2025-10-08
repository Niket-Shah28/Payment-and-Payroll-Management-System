package com.aurionpro.payrollsystem.dto.transaction;

import java.io.Serializable;

import com.aurionpro.payrollsystem.entity.transaction.TransactionStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionChunkRecords implements Serializable {
	private Long employeeId;
	private TransactionStatus status;
	private String failureReason;
}
