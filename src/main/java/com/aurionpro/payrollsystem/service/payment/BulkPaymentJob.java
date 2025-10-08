package com.aurionpro.payrollsystem.service.payment;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.aurionpro.payrollsystem.entity.transaction.PaymentMode;

@Component
public class BulkPaymentJob {
	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job paymentJob;

	@Async
	public void runPayroll(String cloudinaryUrl, String month, Integer year, PaymentMode paymentMode, Long organizationId, String organizationEmail) throws Exception {
	    JobParameters jobParameters = new JobParametersBuilder()
	            .addString("cloudinaryUrl", cloudinaryUrl)
	            .addString("payslipMonth", month)
	            .addString("payslipYear", year.toString())
	            .addString("organizationEmail", organizationEmail)
	            .addString("paymentMode", paymentMode.toString())
	            .addLong("organizationId", organizationId)
	            .addLong("time", System.currentTimeMillis())
	            .toJobParameters();

	    jobLauncher.run(paymentJob, jobParameters);
	}

}
