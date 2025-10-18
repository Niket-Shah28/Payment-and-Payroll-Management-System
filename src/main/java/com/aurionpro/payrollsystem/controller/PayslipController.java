package com.aurionpro.payrollsystem.controller;

import java.io.IOException;
import java.time.Month;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.payrollsystem.dto.employeePayslip.PayslipDetailDto;
import com.aurionpro.payrollsystem.service.employeeInterface.PayslipService;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins="http://localhost:4200")
public class PayslipController {

	@Autowired
    private PayslipService payslipService;

    // Get payslip DTO for a specific month/year
    @GetMapping("/payslip/month/{month}/year/{year}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<PayslipDetailDto> getPayslip(
            @PathVariable Month month,
            @PathVariable Integer year,
            Authentication authentication) {

        Long employeeId = getEmployeeId(authentication);
        PayslipDetailDto payslipDto = payslipService.getPayslipByEmployeeIdMonthYear(employeeId, month, year);
        return ResponseEntity.ok(payslipDto);
    }

    // Download payslip PDF
    @GetMapping("/payslip/download/month/{month}/year/{year}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<ByteArrayResource> downloadPayslipPdf(
            @PathVariable Month month,
            @PathVariable Integer year,
            Authentication authentication) throws IOException {

        Long employeeId = getEmployeeId(authentication);
        PayslipDetailDto payslipDto = payslipService.getPayslipByEmployeeIdMonthYear(employeeId, month, year);

        byte[] pdfBytes = payslipService.generatePayslipPdf(payslipDto);
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);

        String filename = "Payslip_" + payslipDto.getEmployeeName().replaceAll("\\s+", "_")
                + "_" + payslipDto.getPayslipMonth() + "_" + payslipDto.getYear() + ".pdf";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdfBytes.length)
                .body(resource);
    }

    // Utility to extract employeeId from Authentication
    private Long getEmployeeId(Authentication authentication) {
        // Replace with your actual logic if stored differently
        return (Long) authentication.getDetails();
    }
}
