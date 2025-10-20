package com.aurionpro.payrollsystem.service.organization;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aurionpro.payrollsystem.dto.employee.BusinessUnitDto;
import com.aurionpro.payrollsystem.dto.employee.DepartmentDto;
import com.aurionpro.payrollsystem.dto.employee.EmailLoginInfoDto;
import com.aurionpro.payrollsystem.dto.employee.EmployeeDesignationUpdateDto;
import com.aurionpro.payrollsystem.dto.employee.EmployeeRequestDto;
import com.aurionpro.payrollsystem.dto.employee.EmployeeRoleDto;
import com.aurionpro.payrollsystem.dto.employee.EmployeeSalaryUpdateDto;
import com.aurionpro.payrollsystem.dto.employee.EmployeeUploadDataDto;
import com.aurionpro.payrollsystem.dto.organization.EmployeeDto;
import com.aurionpro.payrollsystem.dto.organization.EmployeePageResponseDto;
import com.aurionpro.payrollsystem.dto.organization.OrganizationBankAccountDto;
import com.aurionpro.payrollsystem.dto.organization.OrganizationBankAccountResponseDto;
import com.aurionpro.payrollsystem.dto.organization.OrganizationUpdateBankAccountDto;
import com.aurionpro.payrollsystem.dto.payment.PaymentRequestDto;
import com.aurionpro.payrollsystem.dto.transaction.PaymentRecipientData;
import com.aurionpro.payrollsystem.dto.vendor.VendorDto;
import com.aurionpro.payrollsystem.dto.vendor.VendorResponseDto;
import com.aurionpro.payrollsystem.entity.bankAccount.OrganizationBankAccount;
import com.aurionpro.payrollsystem.entity.employee.Employee;
import com.aurionpro.payrollsystem.entity.employee.EmployeeDesignationRole;
import com.aurionpro.payrollsystem.entity.employee.EmployeeSalary;
import com.aurionpro.payrollsystem.entity.employee.Status;
import com.aurionpro.payrollsystem.entity.organization.Organization;
import com.aurionpro.payrollsystem.entity.transaction.PaymentMode;
import com.aurionpro.payrollsystem.entity.transaction.PaymentRequest;
import com.aurionpro.payrollsystem.entity.vendor.Vendor;
import com.aurionpro.payrollsystem.entity.vendor.VendorContract;
import com.aurionpro.payrollsystem.exception.BatchProcessingException;
import com.aurionpro.payrollsystem.exception.OrganizationException;
import com.aurionpro.payrollsystem.repository.BusinessUnitRepository;
import com.aurionpro.payrollsystem.repository.DepartmentRepository;
import com.aurionpro.payrollsystem.repository.EmployeeRepository;
import com.aurionpro.payrollsystem.repository.EmployeeRoleRepository;
import com.aurionpro.payrollsystem.repository.EmployeeSalaryRepository;
import com.aurionpro.payrollsystem.repository.OrganizationBankAccountRepository;
import com.aurionpro.payrollsystem.repository.PaymentRequestRepository;
import com.aurionpro.payrollsystem.repository.VendorContractRepository;
import com.aurionpro.payrollsystem.repository.VendorRepository;
import com.aurionpro.payrollsystem.service.email.EmailService;
import com.aurionpro.payrollsystem.service.payment.BulkPaymentJob;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManager;

@Service
public class OrganizationServiceImpl implements OrganizationService{
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private BusinessUnitRepository businessUnitRepository;
	
	@Autowired
	private EmployeeRoleRepository employeeRoleRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private OrganizationBankAccountRepository organizationBankAccountRepository;
	
	@Autowired
	private PaymentRequestRepository paymentRequestRepository;
	
	@Autowired
	private EmployeeSalaryRepository employeeSalaryRepository;
	
	@Autowired
	private VendorRepository vendorRepository;
	
	@Autowired
	private VendorContractRepository vendorContractRepository;
	
	@Autowired
    private BulkPaymentJob bulkPaymentJob;

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public void addDepartment(String departmentName, Long organizationId) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("add_department");
		System.out.println(departmentName);
		Map<String, Object> inParams = new HashMap<>();
		inParams.put("p_department_name", departmentName);
		inParams.put("p_organization_id", organizationId);
		
		Map<String, Object> outParams = jdbcCall.execute(inParams);
		
		Boolean success = (Boolean) outParams.get("o_success");
        String message = (String) outParams.get("o_message");
        
        if(!success) {
        	throw new OrganizationException(message, HttpStatus.CONFLICT);
        }
        return;
	}

	@Override
	public void addBusinessUnit(String businessUnitName, Long organizationId) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("add_business_unit");
		System.out.println(businessUnitName);
		Map<String, Object> inParams = new HashMap<>();
		inParams.put("p_business_unit_name", businessUnitName);
		inParams.put("p_organization_id", organizationId);
		
		Map<String, Object> outParams = jdbcCall.execute(inParams);
		
		Boolean success = (Boolean) outParams.get("o_success");
        String message = (String) outParams.get("o_message");
        
        if(!success) {
        	throw new OrganizationException(message, HttpStatus.CONFLICT);
        }
        return;
	}

	@Override
	public void addEmployeeRoles(String role, Long organizationId) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("add_organization_employee_role");
		System.out.println(role);
		Map<String, Object> inParams = new HashMap<>();
		inParams.put("p_role_name", role);
		inParams.put("p_organization_id", organizationId);
		
		Map<String, Object> outParams = jdbcCall.execute(inParams);
		
		Boolean success = (Boolean) outParams.get("o_success");
        String message = (String) outParams.get("o_message");
        
        if(!success) {
        	throw new OrganizationException(message, HttpStatus.CONFLICT);
        }
        return;
	}

	@Override
	public List<DepartmentDto> getOrganizationDepartments(Long organizationId) {
		return departmentRepository.getOrganizationActiveDepartments(organizationId);
	}

	@Override
	public List<BusinessUnitDto> getOrganizationBusinessUnits(Long organizationId) {
		return businessUnitRepository.getOrganizationActiveBusinessUnits(organizationId);
	}

	@Override
	public List<EmployeeRoleDto> getOrganizationRoles(Long organizationId) {
		return employeeRoleRepository.getOrganizationActiveRoles(organizationId);
	}
	
	@Override
	public void bulkUploadEmployeeData(MultipartFile file, Long organizationId) {
		List<EmployeeUploadDataDto> employeeUploadData = ParseCsvData.parseCsv(file);
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("bulk_save_employee_data");
		Map<String, Object> inParams = new HashMap<>();
		inParams.put("p_organization_id", organizationId);
		try {
			inParams.put("p_employee_data_json", objectMapper.writeValueAsString(employeeUploadData));
		} catch (JsonProcessingException e) {
		}
		
		Map<String, Object> outParams = jdbcCall.execute(inParams);
		List<EmailLoginInfoDto> data = null;
		try {
			data = objectMapper.readValue((String) outParams.get("o_employee_account_creation_data"), new TypeReference<List<EmailLoginInfoDto>>() {});
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		};
	
		//data.stream().forEach(a ->emailService.sendEmail("employee_joining_email_template.html", a, "Welcome To our Payroll Portal"));
	}

	@Override
	public void updateDepartment(Long departmentId, String departmentName) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("update_department");
		Map<String, Object> inParams = new HashMap<>();
		inParams.put("p_department_id", departmentId);
		inParams.put("p_new_department_name", departmentName);
		
		Map<String, Object> outParams = jdbcCall.execute(inParams);
		
		Boolean success = (Boolean) outParams.get("o_success");
        String message = (String) outParams.get("o_message");
        
        if(!success) {
        	throw new OrganizationException(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return;
	}

	@Override
	public void updateBusinessUnit(Long businessUnitId, String businessUnitName) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("update_business_unit");
		Map<String, Object> inParams = new HashMap<>();
		inParams.put("p_business_unit_id", businessUnitId);
		inParams.put("p_new_business_unit_name", businessUnitName);
		
		Map<String, Object> outParams = jdbcCall.execute(inParams);
		
		Boolean success = (Boolean) outParams.get("o_success");
        String message = (String) outParams.get("o_message");
        
        if(!success) {
        	throw new OrganizationException(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return;
	}

	@Override
	public void updateEmployeeRoles(Long roleId, String role) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("update_employee_roles");
		Map<String, Object> inParams = new HashMap<>();
		inParams.put("p_role_id", roleId);
		inParams.put("p_new_role_name", role);
		
		Map<String, Object> outParams = jdbcCall.execute(inParams);
		
		Boolean success = (Boolean) outParams.get("o_success");
        String message = (String) outParams.get("o_message");
        
        if(!success) {
        	throw new OrganizationException(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return;
	}

	@Override
	public void removeDepartment(Long departmentId) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("remove_department");
		Map<String, Object> inParams = new HashMap<>();
		inParams.put("p_department_id", departmentId);
		
		Map<String, Object> outParams = jdbcCall.execute(inParams);
		
		Boolean success = (Boolean) outParams.get("o_success");
        String message = (String) outParams.get("o_message");
        
        if(!success) {
        	throw new OrganizationException(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return;
	}

	@Override
	public void removeBusinessUnit(Long businessUnitId) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("remove_business_unit");
		Map<String, Object> inParams = new HashMap<>();
		inParams.put("p_business_unit_id", businessUnitId);
		
		Map<String, Object> outParams = jdbcCall.execute(inParams);
		
		Boolean success = (Boolean) outParams.get("o_success");
        String message = (String) outParams.get("o_message");
        
        if(!success) {
        	throw new OrganizationException(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return;
	}

	@Override
	public void removeEmployeeRoles(Long roleId) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("remove_employee_roles");
		Map<String, Object> inParams = new HashMap<>();
		inParams.put("p_role_id", roleId);
		
		Map<String, Object> outParams = jdbcCall.execute(inParams);
		
		Boolean success = (Boolean) outParams.get("o_success");
        String message = (String) outParams.get("o_message");
        
        if(!success) {
        	throw new OrganizationException(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return;
	}

	@Override
	public void processPaymentRequest(Long paymentRequestId, Status status) {
		PaymentRequest request = paymentRequestRepository.findByPaymentRequestIdAndStatus(paymentRequestId, Status.PENDING)
				.orElseThrow(()->new OrganizationException("REQUEST ID NOT FOUND", HttpStatus.NOT_FOUND));
		request.setStatus(status);
		
		request = paymentRequestRepository.save(request);      
		
		if(request.getStatus() == Status.APPROVED) {
			if(request.getSinglePayment() == false) {
				try {
					bulkPaymentJob.runPayroll(request.getPaymentFileUrl(), request.getMonth().toString(), 
							request.getYear(), PaymentMode.NEFT, request.getOrganizationId().getOrganizationId(), request.getOrganizationId().getEmail());
				} catch (Exception e) {
					throw new BatchProcessingException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
				};
			}
			else {
				SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
		                .withProcedureName("processs_single_payment");
				Map<String, Object> inParams = new HashMap<>();
				inParams.put("p_recipient_account_number", request.getRecipientAccountNumber());
				inParams.put("p_recipient_ifsc", request.getRecipientIfscCode());
				inParams.put("p_recipient_bank_name", request.getRecipientBankName());
				inParams.put("p_recipient_account_holder_name", request.getRecipientAccountHolderName());
				inParams.put("p_organization_id", request.getOrganizationId());
				inParams.put("p_amount", request.getAmount());				
				inParams.put("p_payment_mode", request.getPaymentMode().toString());
				inParams.put("p_payment_recipient_type", request.getPaymentRecipientType().toString());
				
				Map<String, Object> outParams = jdbcCall.execute(inParams);
				
				Boolean success = (Boolean) outParams.get("o_success");
		        String message = (String) outParams.get("o_message");
		        
		        String paymentRecipientType = request.getPaymentRecipientType().toString().substring(0, 1).toUpperCase()+request.getPaymentRecipientType().toString().substring(1).toLowerCase();
		        
		        String final_email_message = "Dear "+request.getOrganizationId().getOrganizationName()+"\n"
		        							 +"Your Payment Request for "+paymentRecipientType+
		        							 ": "+request.getRecipientAccountHolderName()+
		        							 " "+((success)?"is completed successfully.":" has failed due to "+message.toLowerCase());
		        emailService.sendSingleTransactionEmail(request.getOrganizationId().getEmail(), final_email_message, "Payment Request Status");			}
		}
	}

	@Override
	public void addOrganizationBankAccount(Long organizationId, OrganizationBankAccountDto dto) {
		Optional<OrganizationBankAccount> bankAccount = organizationBankAccountRepository.findByOrganization_OrganizationIdAndIsActiveTrue(organizationId);
		Organization orgRef = entityManager.getReference(Organization.class, organizationId);
		OrganizationBankAccount account;
		if(bankAccount.isEmpty() || bankAccount.get().getIsActive()) {
			
			account = modelMapper.map(dto,OrganizationBankAccount.class);
			account.setOrganization(orgRef);
			organizationBankAccountRepository.save(account);
		}
		else {
			throw new OrganizationException("BANK ACCOUNT ALREADY EXISTS", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public void updateBankAccount(Long accountId, OrganizationUpdateBankAccountDto dto) {
		OrganizationBankAccount bankAccount = organizationBankAccountRepository.findByAccountIdAndIsActiveTrue(accountId)
				.orElseThrow(()->new OrganizationException("NO ACCOUNT FOUND WITH ID: "+accountId, HttpStatus.BAD_REQUEST));
		System.out.println(dto);
		modelMapper.getConfiguration()
	    .setSkipNullEnabled(true);
		
		modelMapper.map(dto, bankAccount);
		organizationBankAccountRepository.save(bankAccount);
		return;
	}

	@Override
	public void removeOrganizationBankAccount(Long accountId) {
		OrganizationBankAccount bankAccount = organizationBankAccountRepository.findByAccountIdAndIsActiveTrue(accountId)
				.orElseThrow(()->new OrganizationException("NO ACCOUNT FOUND WITH ID: "+accountId, HttpStatus.BAD_REQUEST));
		
		bankAccount.setIsActive(false);
		organizationBankAccountRepository.save(bankAccount);
		return;
	}

	@Override
	public OrganizationBankAccountResponseDto getOrganizationBankAccount(Long organizationId) {
		Optional<OrganizationBankAccount> bankAccount = organizationBankAccountRepository.findByOrganization_OrganizationIdAndIsActiveTrue(organizationId);
		if(bankAccount.isEmpty()) {
			return null;
		}
		return modelMapper.map(bankAccount, OrganizationBankAccountResponseDto.class);
	}

	@Override
	public void depositAmount(Long accountId, Double amount) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("deposit_amount");
		Map<String, Object> inParams = new HashMap<>();
		inParams.put("p_account_id", accountId);
		inParams.put("p_amount", amount);
		
		Map<String, Object> outParams = jdbcCall.execute(inParams);
		
		Boolean success = (Boolean) outParams.get("o_success");
        String message = (String) outParams.get("o_message");
        
        if(!success) {
        	throw new OrganizationException(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return;
	}

	@Override
	public void addSingleEmployee(Long organizationId, EmployeeRequestDto emp) {
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("add_single_employee");
		Map<String, Object> params = new HashMap<>();

		params.put("p_organization_id", organizationId);
        params.put("p_first_name", emp.getFirstName());
        params.put("p_middle_name", emp.getMiddleName());
        params.put("p_last_name", emp.getLastName());
        params.put("p_gender", emp.getGender().toString());
        params.put("p_salutation", emp.getSalutation().toString());
        params.put("p_spouse", emp.getSpouse());
        params.put("p_date_of_birth", emp.getDateOfBirth());
        params.put("p_blood_group", emp.getBloodGroup());
        params.put("p_nationality", emp.getNationality());
        params.put("p_pan_number", emp.getPanNumber());
        params.put("p_aadhar_number", emp.getAadharNumber());
        params.put("p_manager_id", emp.getManagerId());
        params.put("p_office_email", emp.getOfficeEmail());
        params.put("p_personal_email", emp.getPersonalEmail());
        params.put("p_phone_number", emp.getPhoneNumber());
        params.put("p_basic_salary", emp.getBasicSalary());
        params.put("p_house_rent_allowance", emp.getHouseRentAllowance());
        params.put("p_dearness_allowance", emp.getDearnessAllowance());
        params.put("p_provident_fund", emp.getProvidentFund());
        params.put("p_other_allowance", emp.getOtherAllowance());
        params.put("p_final_salary", emp.getFinalSalary());
        params.put("p_grade", emp.getGrade());
        params.put("p_employee_role_id", emp.getEmployeeRoleId());
        params.put("p_business_unit_id", emp.getBusinessUnitId());
        params.put("p_department_id", emp.getBusinessUnitId());

        Map<String, Object> result = jdbcCall.execute(params);

        Boolean success = (Boolean) result.get("o_success");
        String message = (String) result.get("o_message");
        
        if(!success) {
        	throw new OrganizationException(message, HttpStatus.BAD_REQUEST);
        }
	}

	@Override
	public void updateEmployeeSalary(Long employeeId, EmployeeSalaryUpdateDto dto) {
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(()->new OrganizationException("Employee Not Found", HttpStatus.BAD_REQUEST));
		
		EmployeeSalary salary = employee.getSalary();
		
		modelMapper.getConfiguration().setSkipNullEnabled(true);
		modelMapper.map(dto, salary);

		employeeSalaryRepository.save(salary);	
	
	}

	@Override
	public void updateEmployeeDesignation(Long employeeId, EmployeeDesignationUpdateDto dto) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("update_employee_designation");
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("p_employee_id", employeeId);
		params.put("p_business_unit_id", dto.getBusinessUnitId());
		params.put("p_department_id", dto.getDepartmentId());
		params.put("p_employee_role_id", dto.getEmployeeRoleId());
		params.put("p_grade", dto.getGrade());
		
		Map<String, Object> result = jdbcCall.execute(params);
		
		Boolean success = (Boolean) result.get("o_success");
        String message = (String) result.get("o_message");
        
        if(!success) {
        	throw new OrganizationException(message, HttpStatus.BAD_REQUEST);
        }
	}

	@Override
	public void removeEmployee(Long employeeId) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("remove_employee");
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("p_employee_id", employeeId);
		
		Map<String, Object> result = jdbcCall.execute(params);
		
		Boolean success = (Boolean) result.get("o_success");
        String message = (String) result.get("o_message");
        
        if(!success) {
        	throw new OrganizationException(message, HttpStatus.BAD_REQUEST);
        }
	}

	@Override
	public void addVendor(VendorDto dto, Long organizationId) {
	    Organization organizationRef = entityManager.getReference(Organization.class, organizationId);

	    Vendor vendor = modelMapper.map(dto, Vendor.class);
	    vendor.setOrganization(organizationRef);

	    VendorContract contract = modelMapper.map(dto, VendorContract.class);
	    contract.setVendorId(vendor); // set the back-reference

	    vendor.setContract(contract);

	    vendorRepository.save(vendor); // cascading saves contract too
	}

	@Override
	public List<VendorResponseDto> getVendors(Long organizationId) {
		List<VendorResponseDto> vendors = vendorRepository.getVendors(organizationId);		
		return vendors;
	}

	@Override
	public void removeVendor(Long vendorId) {
		Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(()->new OrganizationException("Vendor not found", HttpStatus.BAD_REQUEST));
		vendor.setIsActive(false);
		vendor.getContract().setIsActive(false);
		System.out.println(vendor.getIsActive());
		vendorRepository.save(vendor);
	}

	@Override
	public VendorDto getVendor(Long vendorId) {
		Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(()->new OrganizationException("Vendor not found", HttpStatus.BAD_REQUEST));
		VendorContract contract = vendor.getContract();
		System.out.println(contract.getContractDocumentUrl());
		VendorDto vendorDto = new VendorDto(
					vendor.getName(),
					vendor.getCinNumber(),
					vendor.getEmail(),
					vendor.getPhoneNumber(),
					vendor.getAddress(),
					vendor.getGstin(),
					vendor.getPan(),
					vendor.getTan(),
					contract.getContractTitle(),
					contract.getStartDate(),
					contract.getEndDate(),
					contract.getContractDocumentUrl()
				);
		return vendorDto;
	}

	@Override
	public void addPaymentRequest(Long organizationId, PaymentRequestDto dto) {
		Organization organization = entityManager.getReference(Organization.class, organizationId);
		PaymentRequest request = modelMapper.map(dto, PaymentRequest.class);
		request.setOrganizationId(organization);
		request.setStatus(Status.PENDING);		
		paymentRequestRepository.save(request);
		return;
	}
	
	@Override
	public byte[] getEmployeePayrollData(long organizationId) {
		List<PaymentRecipientData> list;
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("get_employee_payroll_data");

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("p_organization_id", organizationId);

        Map<String, Object> result = jdbcCall.execute(params);
        String json = (String) result.get("payroll_data");
        
        try {
            list =  Arrays.asList(objectMapper.readValue(json, PaymentRecipientData[].class));
        } catch (Exception e) {
            e.printStackTrace();
            list = Collections.emptyList();
        }
        
        try {
			return getPayrollCsv(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private byte[] getPayrollCsv(List<PaymentRecipientData> list) throws IOException {

        StringWriter writer = new StringWriter();
        writer.append("Employee ID,Name,Email,Department,Role,Business Unit,Account Number,IFSC Code,Bank Name,Account Holder,Basic Salary,HRA,DA,PF,Other Allowance,Actual Salary,Final Salary,Attendance\n");

        for (PaymentRecipientData p : list) {
            writer.append(String.join(",", Arrays.asList(
                    String.valueOf(p.getEmployeeId()), p.getName(), p.getEmail(), p.getDepartment(),
                    p.getRole(), p.getBusinessUnit(), p.getAccountNumber(), p.getIfscCode(),
                    p.getBankName(), p.getAccountHolderName(), String.valueOf(p.getBasicSalary()),
                    String.valueOf(p.getHouseRentAllowance()), String.valueOf(p.getDearnessAllowance()),
                    String.valueOf(p.getProvidentFund()), String.valueOf(p.getOtherAllowance()),
                    String.valueOf(p.getActualSalary()), String.valueOf(p.getFinalSalary()),
                    String.valueOf(p.getAttendance())
            ))).append("\n");
        }

        return writer.toString().getBytes(StandardCharsets.UTF_8);
    }
	
	@Override
	public EmployeePageResponseDto getEmployees(int page, int size, String searchTerm, Long organizationId) {
        Pageable pageable = PageRequest.of(page, size);
        
        Page<Employee> employeePage;
        
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            employeePage = employeeRepository.findAll(pageable);
        } else {
            employeePage = employeeRepository.searchEmployees(searchTerm.trim(), pageable, organizationId);
        }
        
        List<EmployeeDto> dtoList = employeePage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        System.out.println(page);
        
        return new EmployeePageResponseDto(dtoList, employeePage.hasNext(), employeePage.getTotalElements(), employeePage.getTotalPages(), employeePage.getNumber(), employeePage.getSize());
    }
	
	private EmployeeDto convertToDTO(Employee employee) {
		EmployeeDesignationRole role = employee.getDesignation();
		if (role == null) {
		    return new EmployeeDto(
		        employee.getEmployeeId(),
		        employee.getFirstName() + " " + employee.getLastName(),
		        null, null, null
		    );
		}

		return new EmployeeDto(
		    employee.getEmployeeId(),
		    employee.getFirstName() + " " + employee.getLastName(),
		    role.getEmployeeRoleId() != null ? role.getEmployeeRoleId().getRoleName() : null,
		    role.getDepartmentId() != null ? role.getDepartmentId().getDepartmentName() : null,
		    role.getBusinessUnitId() != null ? role.getBusinessUnitId().getBusinessUnitName() : null
		);


    }
}
