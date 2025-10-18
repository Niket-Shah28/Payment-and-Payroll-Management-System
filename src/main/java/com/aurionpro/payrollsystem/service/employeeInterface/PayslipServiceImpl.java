package com.aurionpro.payrollsystem.service.employeeInterface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Month;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.aurionpro.payrollsystem.dto.employeePayslip.PayslipDetailDto;
import com.aurionpro.payrollsystem.entity.employee.Department;
import com.aurionpro.payrollsystem.entity.employee.Employee;
import com.aurionpro.payrollsystem.entity.employee.Payslip;
import com.aurionpro.payrollsystem.repository.PayslipRepository;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import jakarta.persistence.EntityManager;

@Service
public class PayslipServiceImpl implements PayslipService{

	@Autowired
    private PayslipRepository payslipRepository;

    @Autowired
    private SpringTemplateEngine templateEngine;
    
    @Autowired
	private EntityManager entityManager;

    @Override
    public PayslipDetailDto getPayslipByEmployeeIdMonthYear(Long employeeId, Month month, Integer year) {
        // Fetch payslip entity
        Payslip payslip = payslipRepository
                .findByEmployee_EmployeeIdAndMonthAndYear(employeeId, month, year)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Payslip not found for employee %d for %s %d", employeeId, month, year)));

        return convertToDetailDto(payslip);
    }

    @Override
    public byte[] generatePayslipPdf(PayslipDetailDto payslipDto) throws IOException {
        // Prepare Thymeleaf context
        Context context = new Context();
        context.setVariable("payslip", payslipDto);

        // Render HTML template
        String htmlContent = templateEngine.process("payslip_template.html"
        		, context);

        // Convert HTML to PDF
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(htmlContent, null);
            builder.toStream(outputStream);
            builder.run();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new IOException("Error generating PDF: " + e.getMessage(), e);
        }
    }

    // Helper: convert Payslip entity to PayslipDetailDto
    private PayslipDetailDto convertToDetailDto(Payslip payslipData) {
       
        
        return PayslipDetailDto.builder()
                .employeeId(payslipData.getEmployee().getEmployeeId())
                .employeeName(payslipData.getName())
                .department(payslipData.getDepartment())
                .role(payslipData.getRole())
                .businessUnit(payslipData.getBusinessUnit())
                .accountNumber(payslipData.getAccountNumber())
                .ifscCode(payslipData.getIfscCode())
                .bankName(payslipData.getBankName())
                .basicSalary(payslipData.getBasicSalary())
                .houseRentAllowance(payslipData.getHouseRentAllowance())
                .dearnessAllowance(payslipData.getDearnessAllowance())
                .otherAllowances(payslipData.getOtherAllowance())
                .actualSalary(payslipData.getActualSalary())
                .providentFund(payslipData.getProvidentFund())
                .finalSalary(payslipData.getFinalSalary())
                .payslipMonth(payslipData.getMonth())
                .year(payslipData.getYear())
                .build();
    }

}
