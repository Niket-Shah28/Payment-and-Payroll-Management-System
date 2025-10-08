package com.aurionpro.payrollsystem.service.email;

//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.aurionpro.payrollsystem.dto.employee.EmailLoginInfoDto;
import com.aurionpro.payrollsystem.exception.SmtpApiException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	@Autowired 
	private JavaMailSender javaMailSender;
	
	private String loadHtmlTemplate(String filename) throws IOException {
	    ClassPathResource resource = new ClassPathResource("templates/" + filename);
	    return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
	}

	@Async
	public void sendEmail(String template, EmailLoginInfoDto dto, String subject) {
	    MimeMessage mimeMessage = javaMailSender.createMimeMessage();

	    try {
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
	        helper.setFrom("capstonetb7@gmail.com");
	        helper.setTo(dto.getEmail());
	        helper.setSubject(subject);

	        String htmlContent = loadHtmlTemplate(template);
	        htmlContent = htmlContent.replace("{{name}}", dto.getName())
	                                 .replace("{{referenceId}}", dto.getReferenceId());

	        helper.setText(htmlContent, true); 
	        javaMailSender.send(mimeMessage);
	    } catch (MessagingException | IOException e) {
	        throw new SmtpApiException("Error while sending mail: " + e.getLocalizedMessage());
	    }
	}

	public void sendOrganizationPaymentRequestReportMail(long successCount, int failedCount, File csvFile, String organizationEmail) throws MessagingException {
        
		System.out.println("INSIDE MAIL SENDER");
		
		MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(organizationEmail);
        helper.setSubject("Transaction Job Summary");

        String body = String.format(
            "Hello,\n\nThe batch job has completed.\n\n" +
            "Successful transactions: %d\n" +
            "Failed transactions: %d\n\n" +
            "Please find attached CSV of failed transactions.\n\nRegards,\nPayroll System",
            successCount, failedCount
        );
        helper.setText(body);

        helper.addAttachment("failed_transactions.csv", csvFile);

        javaMailSender.send(message);
        //log.info("Job summary mail sent to {}", organizationEmail);
    }
}
