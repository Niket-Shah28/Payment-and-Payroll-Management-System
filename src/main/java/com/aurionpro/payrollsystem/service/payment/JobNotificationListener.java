package com.aurionpro.payrollsystem.service.payment;

//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.aurionpro.payrollsystem.dto.transaction.FailedTransactionRecipientDto;
import com.aurionpro.payrollsystem.service.email.EmailService;

@Component
public class JobNotificationListener implements JobExecutionListener {

	  //private static final Logger log = LoggerFactory.getLogger(JobNotificationListener.class);
	  
	  @Autowired
	  private EmailService emailService;
	
	  @Override
	  public void afterJob(JobExecution jobExecution) {
	    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
	    	
	    	System.out.println("AFTER JOBN");
	    	
	    	@SuppressWarnings("unchecked")
			List<FailedTransactionRecipientDto> failedList = 
	                (List<FailedTransactionRecipientDto>) jobExecution.getExecutionContext().get("failedTransactions");

	    	Long passCount = jobExecution.getExecutionContext().getLong("passCount");
	    	
	    	String organizationEmail = jobExecution.getJobParameters().getString("organizationEmail");
	    	System.out.println("AFTER JOB");
            try {
                File csvFile = generateCsvFile(failedList);
                emailService.sendOrganizationPaymentRequestReportMail(passCount, failedList.size(), csvFile, organizationEmail);
            } catch (Exception e) {
                //log.error("Error sending job completion mail", e);
            }
	    }
	  }
	  
	  @Override
	  public void beforeJob(JobExecution jobExecution) {
		  System.out.println("BEFORE JOB");
		  jobExecution.getExecutionContext().put("passCount", 0L);
		  jobExecution.getExecutionContext().put("failCount", 0L);
		  jobExecution.getExecutionContext().put("failedTransactions", new ArrayList<FailedTransactionRecipientDto>());
	  }
	  
	  private File generateCsvFile(List<FailedTransactionRecipientDto> failedList) throws IOException {
		  System.out.println("FILE GENERATION");
		  File tempFile = File.createTempFile("failed_transactions_", ".csv");
          try (PrintWriter writer = new PrintWriter(tempFile)) {
            // Header
            writer.println("EmployeeId,Name,AccountNumber,IFSCCode,BankName,Amount,FailureReason");
            for (FailedTransactionRecipientDto dto : failedList) {
                writer.printf("%d,%s,%s,%s,%s,%.2f,%s%n",
                        dto.getEmployeeId(),
                        escapeCsv(dto.getName()),
                        dto.getAccountNumber(),
                        dto.getIfscCode(),
                        dto.getBankName(),
                        dto.getAmount(),
                        escapeCsv(dto.getFailureReason())
                );
            }
         }
          return tempFile;
	  }
	  
	  private String escapeCsv(String value) {
	        if (value == null) return "";
	        if (value.contains(",") || value.contains("\"")) {
	            value = value.replace("\"", "\"\"");
	            return "\"" + value + "\"";
	        }
	        return value;
	    }
}
