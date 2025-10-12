package com.aurionpro.payrollsystem.service.payment;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aurionpro.payrollsystem.dto.transaction.FailedTransactionRecipientDto;
import com.aurionpro.payrollsystem.dto.transaction.PaymentRecipientData;
import com.aurionpro.payrollsystem.dto.transaction.PayslipDataDto;
import com.aurionpro.payrollsystem.dto.transaction.TransactionChunkRecords;
import com.aurionpro.payrollsystem.entity.transaction.TransactionStatus;

@Component
@StepScope
public class PaymentChunkListener implements ChunkListener{
	
	@Value("#{jobParameters['payslipMonth']}")
    private String month;
	
	@Value("#{jobParameters['payslipYear']}")
	private Integer year;
	
	@Autowired
	private PayslipService payslipService;
	
	@Override
    public void afterChunk(ChunkContext context) {
        StepExecution stepExecution = context.getStepContext().getStepExecution();

        @SuppressWarnings("unchecked")
        List<PaymentRecipientData> items =
            (List<PaymentRecipientData>) stepExecution.getExecutionContext().get("currentChunkItems");

        @SuppressWarnings("unchecked")
        List<TransactionChunkRecords> chunkRecords =
            (List<TransactionChunkRecords>) stepExecution.getExecutionContext().get("chunkRecords");
        
        if (chunkRecords == null || chunkRecords.isEmpty()) {
            return; 
        }
        
        Long passCount = (Long) stepExecution.getJobExecution().getExecutionContext().get("passCount");
        Long failCount = (Long) stepExecution.getJobExecution().getExecutionContext().get("failCount");
        
        @SuppressWarnings("unchecked")
        List<FailedTransactionRecipientDto> failedTransactions = 
            (List<FailedTransactionRecipientDto>) stepExecution.getJobExecution().getExecutionContext().get("failedTransactions");
            
        if (failedTransactions == null) {
            failedTransactions = new ArrayList<>();
        }
        
        Map<Long, PaymentRecipientData> itemsMap = items.stream()
        		                                   .collect(Collectors.toMap(
                                                        PaymentRecipientData::getEmployeeId,   
										                Function.identity() 
										            ));
        
        for(TransactionChunkRecords record:chunkRecords) {
        	PaymentRecipientData recipient = itemsMap.get(record.getEmployeeId());
        	if(record.getStatus() == TransactionStatus.FAIL) {
        		failCount++;
        		failedTransactions.add(new FailedTransactionRecipientDto(
        					recipient.getEmployeeId(),
        					recipient.getName(),
        					recipient.getAccountNumber(),
        					recipient.getIfscCode(),
        					recipient.getBankName(),
        					recipient.getFinalSalary(),
        					record.getFailureReason()
        				));
        	}
        	else {
        		passCount++;
        		PayslipDataDto payslipData = new PayslipDataDto(
					recipient.getEmployeeId(),
					recipient.getName(),
					recipient.getDepartment(),
					recipient.getRole(),
					recipient.getBusinessUnit(),
					recipient.getAccountNumber(),
					recipient.getIfscCode(),
					recipient.getBankName(),
					recipient.getBasicSalary(),
					recipient.getHouseRentAllowance(),
					recipient.getDearnessAllowance(),
					recipient.getProvidentFund(),
					recipient.getOtherAllowance(),
					recipient.getActualSalary(),
					recipient.getFinalSalary(),
					Month.valueOf(month),
					year
        		);
        		//payslipService.generateAndSavePayslip(payslipData);
        	}
        } 

        stepExecution.getJobExecution().getExecutionContext().put("passCount", passCount);
        stepExecution.getJobExecution().getExecutionContext().put("failCount", failCount);
        stepExecution.getJobExecution().getExecutionContext().put("failedTransactions", failedTransactions);
        
        stepExecution.getExecutionContext().remove("chunkRecords");
        stepExecution.getExecutionContext().remove("currentChunkItems");
    }
}
