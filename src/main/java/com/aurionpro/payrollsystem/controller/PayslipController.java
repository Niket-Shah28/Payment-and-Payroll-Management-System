package com.aurionpro.payrollsystem.controller;

import java.time.Month;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.payrollsystem.dto.employeePayslip.PayslipDto;
import com.aurionpro.payrollsystem.dto.employeePayslip.PayslipSummaryDto;
import com.aurionpro.payrollsystem.service.employeeInterface.PayslipService;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins="http://localhost:4200")
public class PayslipController {

	@Autowired
	private PayslipService payslipService;
	
	@GetMapping("/payslips")
	@PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<PayslipDto>> getAllPayslipsByEmployeeId(Authentication authentication) {
		Long employeeId = (Long) authentication.getDetails();
        List<PayslipDto> payslips = payslipService.getAllPayslipsByEmployeeId(employeeId);
        return ResponseEntity.ok(payslips);
    }
    
   
    @GetMapping("/payslips/summaries")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<PayslipSummaryDto>> getPayslipSummariesByEmployeeId(Authentication authentication) {
    	Long employeeId = (Long) authentication.getDetails();
        List<PayslipSummaryDto> summaries = payslipService.getPayslipSummariesByEmployeeId(employeeId);
        return ResponseEntity.ok(summaries);
    }
    
    
    @GetMapping("/payslips/{payslipId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<PayslipDto> getPayslipById(@PathVariable Long payslipId) {
        PayslipDto payslip = payslipService.getPayslipById(payslipId);
        return ResponseEntity.ok(payslip);
    }
    
   
    @GetMapping("/payslips/timeperiod/month/{month}/year/{year}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<PayslipDto> getPayslipByEmployeeIdMonthYear(
    		@PathVariable Month month,
    		@PathVariable Integer year,
            Authentication authentication) {
    	Long employeeId = (Long) authentication.getDetails();
        PayslipDto payslip = payslipService.getPayslipByEmployeeIdMonthYear(employeeId, month, year);
        return ResponseEntity.ok(payslip);
    }
    
  
    @GetMapping("/payslips/year/{year}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<PayslipDto>> getPayslipsByEmployeeIdAndYear(
    		@PathVariable Integer year,
            Authentication authentication) {
    	Long employeeId = (Long) authentication.getDetails();
        List<PayslipDto> payslips = payslipService.getPayslipsByEmployeeIdAndYear(employeeId, year);
        return ResponseEntity.ok(payslips);
    }
    
   
    @GetMapping("/payslips/latest")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<PayslipDto> getLatestPayslipByEmployeeId(Authentication authentication) {
    	Long employeeId = (Long) authentication.getDetails();
        PayslipDto payslip = payslipService.getLatestPayslipByEmployeeId(employeeId);
        return ResponseEntity.ok(payslip);
    }
    
   
//    @GetMapping("/payslips/{payslipId}/download")
//    @PreAuthorize("hasRole('EMPLOYEE')")
//    public ResponseEntity<byte[]> downloadPayslip(@PathVariable Long payslipId) {
//        byte[] pdfData = payslipService.downloadPayslipPdf(payslipId);
//        String filename = payslipService.getPayslipFilename(payslipId);
//        
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDispositionFormData("attachment", filename);
//        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
//        
//        return new ResponseEntity<>(pdfData, headers, HttpStatus.OK);
//    }
    
   
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            ex.getMessage(),
            System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
   
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            ex.getMessage(),
            System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
    /**
     * Inner class for error response
     */
    @lombok.Data
    @lombok.AllArgsConstructor
    static class ErrorResponse {
        private int status;
        private String message;
        private long timestamp;
    }
}
