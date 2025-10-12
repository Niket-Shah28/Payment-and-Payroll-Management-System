package com.aurionpro.payrollsystem.service.payment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.StepSynchronizationManager;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import com.aurionpro.payrollsystem.dto.transaction.EmployeeTransactionDataDto;
import com.aurionpro.payrollsystem.dto.transaction.PaymentRecipientData;
import com.aurionpro.payrollsystem.dto.transaction.TransactionChunkRecords;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@StepScope
public class PaymentProcedureWriter implements ItemWriter<PaymentRecipientData> {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
    private ObjectMapper objectMapper;
	
	@Value("#{jobParameters['organizationId']}")
    private Long organizationId;
	
	@Value("#{jobParameters['paymentMode']}")
	private String paymentMode;

	@Override
	public void write(Chunk<? extends PaymentRecipientData> chunk) throws Exception {
		List<? extends PaymentRecipientData> items = chunk.getItems();

        StepExecution stepExecution = StepSynchronizationManager.getContext().getStepExecution();
        stepExecution.getExecutionContext().put("currentChunkItems", new ArrayList<>(items));
        
        List<EmployeeTransactionDataDto> employeeTransactionData = items.stream() 
        															.map(i ->
        																new EmployeeTransactionDataDto(
        																	i.getEmployeeId(),
        																	i.getAccountNumber(),
        																	i.getIfscCode(),
        																	i.getAccountHolderName(),
        																	i.getBankName(),
        																	i.getFinalSalary()		
        																)
        															).toList();

        String chunkJson = objectMapper.writeValueAsString(employeeTransactionData);
 
        
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("batch_payment");
        
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("p_organization_id", organizationId);
        inParams.put("p_employee_chunk", chunkJson);
        inParams.put("p_payment_mode", paymentMode);

        Map<String, Object> out = jdbcCall.execute(inParams);

        List<TransactionChunkRecords> records = objectMapper.readValue(
        		(String) out.get("o_transaction_status_chunk"), 
                new TypeReference<List<TransactionChunkRecords>>() {}
        );

        stepExecution.getExecutionContext().put("chunkRecords", records);
    }
}
