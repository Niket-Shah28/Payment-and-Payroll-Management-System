package com.aurionpro.payrollsystem.service.payment;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.aurionpro.payrollsystem.dto.transaction.PayslipDataDto;
import com.aurionpro.payrollsystem.entity.employee.Employee;
import com.aurionpro.payrollsystem.entity.employee.Payslip;
import com.aurionpro.payrollsystem.repository.PayslipRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import jakarta.persistence.EntityManager;

public class PayslipService {
	
	@Autowired
	private PayslipRepository payslipRepository;
	
	@Autowired
    private Cloudinary cloudinary;
	
	@Autowired
	private  SpringTemplateEngine templateEngine;
	
	@Autowired
	private EntityManager entityManager;

	@Async
	public void generateAndSavePayslip(PayslipDataDto payslipData) throws Exception {
		
	  System.out.println("GENERATING PAYSLIP");

      Context context = new Context();
      context.setVariable("payslip", payslipData);
      String htmlContent = templateEngine.process("payslip_template", context);

      byte[] pdfBytes = convertHtmlToPdf(htmlContent);

      
      @SuppressWarnings("rawtypes")
      Map uploadResult = cloudinary.uploader().upload(pdfBytes,
  	        ObjectUtils.asMap(
  	                "resource_type", "raw"
  	        ));

      String payslipUrl = (String) uploadResult.get("secure_url");

      Payslip payslip = new Payslip();
      Employee employeeRef = entityManager.find(Employee.class, payslipData.getEmployeeId());
      payslip.setEmployee(employeeRef);
      payslip.setMonth(payslipData.getMonth());
      payslip.setYear(payslipData.getYear());
      payslip.setPayslipUrl(payslipUrl);
      payslipRepository.save(payslip);
      return;
	}
	
	
	// ... other imports ...

	private byte[] convertHtmlToPdf(String htmlContent) throws Exception {
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    
	    // Note: Use the full path or import if needed
	    com.openhtmltopdf.pdfboxout.PdfRendererBuilder builder = new com.openhtmltopdf.pdfboxout.PdfRendererBuilder();

	    // No explicit size/margin calls needed here! The CSS @page rule handles it.
	    
	    builder.withHtmlContent(htmlContent, "classpath:/");
	    builder.toStream(outputStream);
	    builder.run();
	    
	    return outputStream.toByteArray();
	}
}
	
