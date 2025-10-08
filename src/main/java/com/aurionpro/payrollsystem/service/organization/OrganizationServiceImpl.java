package com.aurionpro.payrollsystem.service.organization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aurionpro.payrollsystem.dto.employee.BusinessUnitDto;
import com.aurionpro.payrollsystem.dto.employee.DepartmentDto;
import com.aurionpro.payrollsystem.dto.employee.EmailLoginInfoDto;
import com.aurionpro.payrollsystem.dto.employee.EmployeeRoleDto;
import com.aurionpro.payrollsystem.dto.employee.EmployeeUploadDataDto;
import com.aurionpro.payrollsystem.dto.organization.OrganizationBankAccountDto;
import com.aurionpro.payrollsystem.dto.organization.OrganizationBankAccountResponseDto;
import com.aurionpro.payrollsystem.dto.organization.OrganizationUpdateBankAccountDto;
import com.aurionpro.payrollsystem.entity.bankAccount.OrganizationBankAccount;
import com.aurionpro.payrollsystem.entity.employee.Status;
import com.aurionpro.payrollsystem.entity.organization.Organization;
import com.aurionpro.payrollsystem.entity.transaction.PaymentMode;
import com.aurionpro.payrollsystem.entity.transaction.PaymentRequest;
import com.aurionpro.payrollsystem.exception.OrganizationException;
import com.aurionpro.payrollsystem.repository.BusinessUnitRepository;
import com.aurionpro.payrollsystem.repository.DepartmentRepository;
import com.aurionpro.payrollsystem.repository.EmployeeRepository;
import com.aurionpro.payrollsystem.repository.EmployeeRoleRepository;
import com.aurionpro.payrollsystem.repository.OrganizationBankAccountRepository;
import com.aurionpro.payrollsystem.repository.PaymentRequestRepository;
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
	
		data.stream().forEach(a ->emailService.sendEmail("employee_joining_email_template.html", a, "Welcome To our Payroll Portal"));
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
			try {
				bulkPaymentJob.runPayroll(request.getPaymentFileUrl(), request.getMonth().toString(), 
						request.getYear(), PaymentMode.NEFT, request.getOrganizationId().getOrganizationId(), request.getOrganizationId().getEmail());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
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
	
	
}
