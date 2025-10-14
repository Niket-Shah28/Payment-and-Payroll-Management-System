package com.aurionpro.payrollsystem.service.payment;

import java.util.List;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.aurionpro.payrollsystem.dto.transaction.FailedTransactionRecipientDto;
import com.aurionpro.payrollsystem.dto.transaction.PaymentRecipientData;

@Component
@StepScope
public class PaymentProcessor implements ItemProcessor<PaymentRecipientData, PaymentRecipientData> {
	
	private JobExecution jobExecution;
	private Long failCount;
    private List<FailedTransactionRecipientDto> failedList;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.jobExecution = stepExecution.getJobExecution();
        
        this.failCount = (Long) jobExecution.getExecutionContext().get("failCount");
        
        @SuppressWarnings("unchecked")
		List<FailedTransactionRecipientDto> contextFailedList =
                (List<FailedTransactionRecipientDto>) jobExecution.getExecutionContext().get("failedTransactions");
        this.failedList = contextFailedList;
    }
    
    @Override
    public PaymentRecipientData process(PaymentRecipientData item) {
    	
        if (item.getFinalSalary() == null || item.getFinalSalary() <= 0 || 
        	item.getAccountNumber() == null || item.getAccountNumber().trim() == "" ||
        	item.getIfscCode() == null || item.getIfscCode().trim() == "" ||
        	item.getAccountHolderName() == null || item.getAccountHolderName().trim() == "" ||
        	item.getBankName() == null || item.getBankName().trim() == "") {
        	
        	StringBuilder reason = new StringBuilder();
            if (item.getFinalSalary() == null || item.getFinalSalary() <= 0) reason.append("INVALID AMOUNT; ");
            if (item.getAccountNumber() == null || item.getAccountNumber().trim() == "") reason.append("ACCOUNT NUMBER MISSING; ");
            if (item.getIfscCode() == null || item.getIfscCode().trim() == "") reason.append("IFSC CODE MISSING; ");
            if (item.getAccountHolderName() == null || item.getAccountHolderName().trim() == "") reason.append("ACCOUNT HOLDER NAME MISSING; ");
            if (item.getBankName() == null || item.getBankName().trim() == "") reason.append("BANK NAME MISSING; ");

            jobExecution.getExecutionContext().put("failCount", failCount + 1);
            failedList.add(new FailedTransactionRecipientDto(item.getEmployeeId(), item.getName(), 
            		item.getAccountNumber(), item.getIfscCode(), item.getBankName(), item.getFinalSalary(), 
            		reason.toString().trim()));
            return null;
        }

        return item;
    }
}
