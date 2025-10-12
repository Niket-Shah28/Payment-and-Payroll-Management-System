package com.aurionpro.payrollsystem.config;

import java.net.MalformedURLException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.UrlResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.aurionpro.payrollsystem.dto.transaction.PaymentRecipientData;
import com.aurionpro.payrollsystem.service.payment.BulkPaymentJob;
import com.aurionpro.payrollsystem.service.payment.JobNotificationListener;
import com.aurionpro.payrollsystem.service.payment.PaymentChunkListener;
import com.aurionpro.payrollsystem.service.payment.PaymentProcedureWriter;

@Configuration
public class BatchConfig {

	@Autowired
    private JobNotificationListener jobNotificationListener;
	
	@Bean
    @StepScope
    FlatFileItemReader<PaymentRecipientData> reader(@Value("#{jobParameters['cloudinaryUrl']}") String cloudinaryUrl) throws MalformedURLException {
        return new FlatFileItemReaderBuilder<PaymentRecipientData>()
                .name("paymentRequestReader")
                .resource(new UrlResource(cloudinaryUrl))
                .delimited()
                .names("employeeId", "name", "email", "department", "role", "businessUnit", "accountNumber",
                        "ifscCode", "bankName", "accountHolderName", "basicSalary", "houseRentAllowance",
                        "dearnessAllowance", "providentFund", "otherAllowance", "actualSalary", "finalSalary",
                        "attendance")
                .targetType(PaymentRecipientData.class)
                .linesToSkip(1)
                .build();
    }

    @Bean
    Step paymentStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            FlatFileItemReader<PaymentRecipientData> reader,
            ItemProcessor<PaymentRecipientData, PaymentRecipientData> processor,
            PaymentProcedureWriter writer,
            PaymentChunkListener listener
    ) {
        return new StepBuilder("paymentStep", jobRepository)
                .<PaymentRecipientData, PaymentRecipientData>chunk(1500, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(listener)
                .build();
    }

    @Bean
    Job paymentJob(JobRepository jobRepository, Step paymentStep) {
        return new JobBuilder("paymentJob", jobRepository)
                .start(paymentStep)
                .listener(jobNotificationListener) 
                .build();
    }

    @Bean
    BulkPaymentJob bulkPaymentJob() {
        return new BulkPaymentJob();
    }
}
