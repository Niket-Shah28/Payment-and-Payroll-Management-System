package com.aurionpro.payrollsystem.service.employeeInterface;

import java.io.ByteArrayOutputStream;	
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.aurionpro.payrollsystem.dto.employeePayslip.PayslipDetailDto;
import com.aurionpro.payrollsystem.dto.employeePayslip.PayslipDto;
import com.aurionpro.payrollsystem.dto.employeePayslip.PayslipSummaryDto;
import com.aurionpro.payrollsystem.entity.bankAccount.EmployeeBankInfo;
import com.aurionpro.payrollsystem.entity.employee.Employee;
import com.aurionpro.payrollsystem.entity.employee.EmployeeDesignationRole;
import com.aurionpro.payrollsystem.entity.employee.EmployeeSalary;
import com.aurionpro.payrollsystem.entity.employee.Payslip;
import com.aurionpro.payrollsystem.repository.EmployeeBankInfoRepository;
import com.aurionpro.payrollsystem.repository.EmployeeDesignationRoleRepository;
import com.aurionpro.payrollsystem.repository.EmployeeRepository;
import com.aurionpro.payrollsystem.repository.EmployeeSalaryRepository;
import com.aurionpro.payrollsystem.repository.PayslipRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class PayslipServiceImpl implements PayslipService{

	@Autowired
	private PayslipRepository payslipRepository;
	
	
//	@Autowired
//    private  EmployeeRepository employeeRepository; 
//	@Autowired
//    private  EmployeeSalaryRepository salaryRepository;
//    @Autowired
//    private  EmployeeBankInfoRepository bankInfoRepository;
//	@Autowired
//    private  EmployeeDesignationRoleRepository roleRepository;
//	@Autowired
//    private  SpringTemplateEngine templateEngine;
//	@Autowired
//    private  Cloudinary cloudinary; 
	
//	@Autowired
//	private RestTemplate restTemplate = new RestTemplate();
	
	
//	 // Helper method to aggregate all necessary data
//    private PayslipDetailDto buildPayslipData(Employee employeeId, Month month, int year) {
//        
//        EmployeeSalary salary = salaryRepository.findByEmployeeId(employeeId)
//            .orElseThrow(() -> new EntityNotFoundException("Salary not found for employee: " + employeeId.getEmployeeId()));
//
//        EmployeeBankInfo bankInfo = bankInfoRepository.findByEmployee(employeeId)
//            .orElseThrow(() -> new EntityNotFoundException("Bank info not found for employee: " + employeeId.getEmployeeId()));
//
//        EmployeeDesignationRole roleInfo = roleRepository.findByEmployeeId(employeeId)
//            .orElseThrow(() -> new EntityNotFoundException("Role info not found for employee: " + employeeId.getEmployeeId()));
//
//        // --- Calculation and Mapping ---
//        Double basic = Double.valueOf(salary.getBasicSalary());
//        Double hra = Double.valueOf(salary.getHouseRentAllowance());
//        Double da = Double.valueOf(salary.getDearnessAllowance());
//        Double other = Double.valueOf(salary.getOtherAllowance());
//        Double pf = Double.valueOf(salary.getProvidentFund());
//
//        Double actualSalary = basic.add(hra).add(da).add(other);
//        Double finalSalary = actualSalary.subtract(pf); // Final Salary (In Hand)
//        
//        return PayslipDetailDto.builder()
//            .payslipMonth(month)
//            .employeeId(employeeId.getEmployeeId())
//            .employeeName(employeeId.getFirstName()) 
//            // Assuming employee has a getName() method
//            .department(roleInfo.getDepartmentId().getDepartmentName()) // Assuming Department entity has getName()
//            .role(roleInfo.getEmployeeRoleId().getRoleName())
//            .businessUnit(roleInfo.getBusinessUnitId().getBusinessUnitName()) // Assuming BU entity has getName()
//            .accountNumber(bankInfo.getAccount().getAccountNumber())
//            .ifscCode(bankInfo.getAccount().getIfscCode())
//            .bankName(bankInfo.getAccount().getBankName())
//            .basicSalary(basic)
//            .houseRentAllowance(hra)
//            .dearnessAllowance(da)
//            .otherAllowances(other)
//            .actualSalary(actualSalary)
//            .providentFund(pf)
//            .finalSalary(finalSalary)
//            .build();
//    }
//
//
//    // 1. Generation Logic (Called by the Batch Job)
//    @Override
//    @Transactional
//    public Payslip generateAndSavePayslip(Employee employeeId, Month month, int year) throws Exception {
//        
//        // 1. Fetch and aggregate data
//        PayslipDetailDto payslipData = buildPayslipData(employeeId, month, year);
//
//        // 2. Generate HTML from Thymeleaf template
//        Context context = new Context();
//        context.setVariable("payslip", payslipData);
//        String htmlContent = templateEngine.process("payslip_template", context);
//
//        // 3. Convert HTML to PDF (Requires a dedicated PDF library like Flying Saucer/iText)
//        byte[] pdfBytes = convertHtmlToPdf(htmlContent);
//
//        // 4. Upload PDF to Cloudinary (Mocked interaction)
//        String payslipUrl = uploadToCloudinary(pdfBytes, employeeId.getEmployeeId(), month, year);
//
//        // 5. Save Payslip entity
//        Payslip payslip = new Payslip();
//        payslip.setEmployeeId(employeeId);
//        payslip.setMonth(month);
//        payslip.setYear(year);
//        payslip.setPayslipUrl(payslipUrl);
//        return payslipRepository.save(payslip);
//    }
//    
//    // 2. Retrieval Logic (Called by the Employee Controller)
//    @Override
//    public Optional<String> getPayslipUrl(Long employeeId, Integer month, Integer year) {
//        Employee employee = employeeRepository.findById(employeeId)
//            .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + employeeId));
//            
//        return payslipRepository.findByEmployeeIdAndMonthAndYear(employee, month, year)
//            .map(Payslip::getPayslipUrl);
//    }
//
//    // --- REAL PDF AND CLOUDINARY METHODS ---
//
//    /** REAL: Converts the HTML content generated by Thymeleaf into a PDF byte array using OpenHTMLToPDF. **/
//    private byte[] convertHtmlToPdf(String htmlContent) throws Exception {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        
//        PdfRendererBuilder builder = new PdfRendererBuilder();
//        
//        // The base URL tells the renderer where to find resources like CSS/fonts if needed.
//        // Since we embedded all CSS, "classpath:/" works fine.
//        builder.withHtmlContent(htmlContent, "classpath:/");
//        builder.toStream(outputStream);
//        builder.run();
//        
//        return outputStream.toByteArray();
//    }
//
	
//    /** REAL: Uploads the generated PDF byte array to Cloudinary and returns the public URL. **/
//    private String uploadToCloudinary(byte[] pdfBytes, Long empId, int month, int year) throws IOException {
//        String filename = String.format("payslip_%d_%d_%d.pdf", empId, year, month);
//        
//        // Use ObjectUtils.asMap to pass parameters, including a unique file name
//        Map uploadResult = cloudinary.uploader().upload(pdfBytes, ObjectUtils.asMap(
//            "resource_type", "auto",
//            "public_id", "payslips/" + filename, // Store under a 'payslips' folder
//            "overwrite", true
//        ));
//        
//        // Return the secure URL provided by Cloudinary
//        return (String) uploadResult.get("secure_url");
//    }
//	
	
	
	
	
	////////
	
	 @Override
	    public List<PayslipDto> getAllPayslipsByEmployeeId(Long employeeId) {
	        List<Payslip> payslips = payslipRepository.findByEmployeeWithDetails(employeeId);
	        return payslips.stream()
	                .map(this::convertToDto)
	                .collect(Collectors.toList());
	    }
	    
	    @Override
	    public PayslipDto getPayslipById(Long payslipId) {
	        Payslip payslip = payslipRepository.findByIdWithDetails(payslipId)
	                .orElseThrow(() -> new RuntimeException("Payslip not found with id: " + payslipId));
	        return convertToDto(payslip);
	    }
	    
	    @Override
	    public PayslipDto getPayslipByEmployeeIdMonthYear(Long employeeId, Month month, Integer year) {
	        validateMonth(month);
	        Payslip payslip = payslipRepository.findByEmployee_EmployeeIdAndMonthAndYear(employeeId, month, year)
	                .orElseThrow(() -> new RuntimeException(
	                    String.format("Payslip not found for employee %d for %s %d", 
	                    employeeId, month, year)));
	        return convertToDto(payslip);
	    }
	    
	    @Override
	    public List<PayslipDto> getPayslipsByEmployeeIdAndYear(Long employeeId, Integer year) {
	        List<Payslip> payslips = payslipRepository.findByEmployee_EmployeeIdAndYearOrderByMonthDesc(employeeId, year);
	        return payslips.stream()
	                .map(this::convertToDto)
	                .collect(Collectors.toList());
	    }
	    
	    @Override
	    public PayslipDto getLatestPayslipByEmployeeId(Long employeeId) {
	        Payslip payslip = payslipRepository.findLatestPayslipByEmployeeId(employeeId)
	                .orElseThrow(() -> new RuntimeException("No payslips found for employee id: " + employeeId));
	        return convertToDto(payslip);
	    }
	    
	    @Override
	    public List<PayslipSummaryDto> getPayslipSummariesByEmployeeId(Long employeeId) {
	        List<Payslip> payslips = payslipRepository.findByEmployee_EmployeeIdOrderByYearDescMonthDesc(employeeId);
	        return payslips.stream()
	                .map(this::convertToSummaryDto)
	                .collect(Collectors.toList());
	    }
	    
//	    @Override
//	    public byte[] downloadPayslipPdf(Long payslipId) {
//	        Payslip payslip = payslipRepository.findById(payslipId)
//	                .orElseThrow(() -> new RuntimeException("Payslip not found with id: " + payslipId));
//	        
//	        try {
//	            URI uri = URI.create(payslip.getPayslipUrl());
//	            try (InputStream in = uri.toURL().openStream()) {
//	                return in.readAllBytes();
//	            }
//	        } catch (IOException e) {
//	            throw new RuntimeException("Failed to download payslip from URL: " + e.getMessage(), e);
//	        } catch (IllegalArgumentException e) {
//	            throw new RuntimeException("Invalid payslip URL: " + e.getMessage(), e);
//	        }
//	    }
	    
	    @Override
	    public String getPayslipFilename(Long payslipId) {
	        Payslip payslip = payslipRepository.findByIdWithDetails(payslipId)
	                .orElseThrow(() -> new RuntimeException("Payslip not found with id: " + payslipId));
	        
	        String employeeName = "";
	        if (payslip.getEmployee() != null) {
	            employeeName = payslip.getEmployee().getFirstName() + "_" + 
	                          payslip.getEmployee().getLastName();
	            employeeName = employeeName.replaceAll("\\s+", "_");
	        }
	        
        Month month = payslip.getMonth();
	        return String.format("Payslip_%s_%s_%d.pdf", 
	                           employeeName, 
	                           month, 
	                           payslip.getYear());
	    }
	    
	    private PayslipDto convertToDto(Payslip payslip) {
	        PayslipDto dto = new PayslipDto();
	        //dto.setPayslipId(payslip.getPayslipId());
	        dto.setMonth(payslip.getMonth());
	       // dto.setMonthName(getMonthName(payslip.getMonth()));
	        dto.setYear(payslip.getYear());
	       // dto.setPayslipUrl(payslip.getPayslipId());
	       // dto.setCreatedAt(payslip.getCreatedAt());
	        //dto.setUpdatedAt(payslip.getUpdatedAt());
	        
	        if (payslip.getEmployee() != null) {
	          //  dto.setEmployeeId(payslip.getEmployeeId().getEmployeeId());
	            dto.setEmployeeName(payslip.getEmployee().getFirstName() + " " + 
	                              payslip.getEmployee().getLastName());
	           // dto.setEmployeeEmail(payslip.getEmployeeId().getEmail());
	        }
	        
	        return dto;
	    }
	    
	    private PayslipSummaryDto convertToSummaryDto(Payslip payslip) {
	        PayslipSummaryDto dto = new PayslipSummaryDto();
	        dto.setPayslipId(payslip.getPayslipId());
	        dto.setMonth(payslip.getMonth());
	        //dto.setMonthName(payslip.getMonth());
	        dto.setYear(payslip.getYear());
	        dto.setPeriod(payslip.getMonth() + " " + payslip.getYear());
	       // dto.setCreatedAt(payslip.getCreatedAt());
	        return dto;
	    }
	    
//	    private String getMonthName(Month month) {
//	        if (month == null || month < 1 || month > 12) {
//	            return "Unknown";
//	        }
//	        return month.name();
//	    }
//	    
	    private void validateMonth(Month month) {
	        if (month == null) {
	            throw new IllegalArgumentException("Month must be between 1 and 12");
	        }
	    }
}
