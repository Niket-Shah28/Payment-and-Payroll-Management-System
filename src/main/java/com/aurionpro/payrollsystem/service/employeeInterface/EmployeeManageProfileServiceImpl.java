package com.aurionpro.payrollsystem.service.employeeInterface;

import org.modelmapper.ModelMapper;	
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aurionpro.payrollsystem.dto.manageEmployeeProfile.EmployeeAddressRequestDto;
import com.aurionpro.payrollsystem.dto.manageEmployeeProfile.EmployeeAddressResponseDto;
import com.aurionpro.payrollsystem.dto.manageEmployeeProfile.EmployeeContactDetailsRequestDto;
import com.aurionpro.payrollsystem.dto.manageEmployeeProfile.EmployeeContactDetailsResponseDto;
import com.aurionpro.payrollsystem.dto.manageEmployeeProfile.ProfileRequestDto;
import com.aurionpro.payrollsystem.dto.manageEmployeeProfile.ProfileResponseDto;
import com.aurionpro.payrollsystem.entity.employee.Employee;
import com.aurionpro.payrollsystem.entity.employee.EmployeeAddress;
import com.aurionpro.payrollsystem.entity.employee.EmployeeContactDetails;
import com.aurionpro.payrollsystem.exception.ResourceNotFoundException;
import com.aurionpro.payrollsystem.repository.EmployeeAddressRepository;
import com.aurionpro.payrollsystem.repository.EmployeeContactDetailsRepository;
import com.aurionpro.payrollsystem.repository.EmployeeRepository;
import com.aurionpro.payrollsystem.service.authentication.AuthServiceImpl;

@Service
public class EmployeeManageProfileServiceImpl implements EmployeeManageProfileService {

	@Autowired
	private EmployeeAddressRepository addressRepository;
	
	@Autowired
	private EmployeeContactDetailsRepository contactRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CloudinaryService cloudinaryService;

	
	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Override
	public EmployeeAddressResponseDto getEmployeeAddress(Long employeeId) {
		Employee employee = employeeRepository.findById(employeeId)
	            .orElseThrow(() -> new ResourceNotFoundException("No employee found with this employee ID: " + employeeId));
	    if (!employee.getIsActive()) {
	        throw new ResourceNotFoundException("Employee with ID " + employeeId + " is no longer active.");
	    }
	    EmployeeAddress address = addressRepository.findByEmployeeId(employee)
	            .orElseThrow(() -> new ResourceNotFoundException("No address found for employee with ID: "));
	    logger.info("Viewing address details of employee");
	    return EmployeeAddressToEmployeeAddressResponseDto(address);
	}

	private EmployeeAddressResponseDto EmployeeAddressToEmployeeAddressResponseDto(EmployeeAddress address) {
	    if (address == null) {
	        return null;
	    }

	    EmployeeAddressResponseDto dto = new EmployeeAddressResponseDto();
	    
	    dto.setCity(address.getCity());
	    dto.setCountry(address.getCountry());
	    dto.setCurrentAddress(address.getCurrentAddress());
	    dto.setPermanentAddress(address.getPermanentAddress());
	    dto.setPincode(address.getPincode());
	    dto.setState(address.getState());
	    
	    return dto;
	}

	@Override
	public EmployeeAddressResponseDto updateEmployeeAddress(Long employeeId, EmployeeAddressRequestDto patchDto) {
		Employee employee = employeeRepository.findById(employeeId)
	            .orElseThrow(() -> new ResourceNotFoundException("No employee found with this employee ID: " + employeeId));
	    if (!employee.getIsActive()) {
	        throw new ResourceNotFoundException("Employee with ID " + employeeId + " is no longer active.");
	    }
	    EmployeeAddress address = addressRepository.findByEmployeeId(employee)
	            .orElseThrow(() -> new ResourceNotFoundException("No address found for employee with ID: "));
	    logger.info("Address details of employee");

		if (patchDto.getCurrentAddress() != null) {
			address.setCurrentAddress(patchDto.getCurrentAddress());
		}
		if (patchDto.getPermanentAddress() != null) {
			address.setPermanentAddress(patchDto.getPermanentAddress());
		}
		if (patchDto.getState() != null) {
			address.setState(patchDto.getState());
		}
		if (patchDto.getCity() != null) {
			address.setCity(patchDto.getCity());
		}
		if (patchDto.getPincode() != null) {
			address.setPincode(patchDto.getPincode());
		}
		if (patchDto.getCountry() != null) {
			address.setCountry(patchDto.getCountry());
		}
		
		

		EmployeeAddress savedAddress = addressRepository.save(address);
		return mapper.map(savedAddress, EmployeeAddressResponseDto.class);
	}

	@Override
	public EmployeeContactDetailsResponseDto getEmployeeContactDetails(Long employeeId) {
		Employee employee = employeeRepository.findById(employeeId)
	            .orElseThrow(() -> new ResourceNotFoundException("No employee found with this employee ID: " + employeeId));
	    if (!employee.getIsActive()) {
	        throw new ResourceNotFoundException("Employee with ID " + employeeId + " is no longer active.");
	    }
	    
	    EmployeeContactDetails contact = contactRepository.findByEmployeeId(employee)
	            .orElseThrow(() -> new ResourceNotFoundException("No contact details found for employee with ID: "));
	    logger.info("Viewing contact details of employee");
	    return EmployeeContactDetailsToEmployeeContactDetailsResponseDto(contact);
	}

	private EmployeeContactDetailsResponseDto EmployeeContactDetailsToEmployeeContactDetailsResponseDto(
			EmployeeContactDetails contact) {
		if (contact == null) {
	        return null;
	    }

	    EmployeeContactDetailsResponseDto dto = new EmployeeContactDetailsResponseDto();
	    
	    dto.setAlternateMobileNumber(contact.getAlternateMobileNumber());
	    dto.setEmail(contact.getEmail());
	    dto.setEmergencyContact(contact.getEmergencyContact());
	    dto.setEmergencyContactRelation(contact.getEmergencyContactRelation());
	    dto.setOfficeEmail(contact.getOfficeEmail());
	    dto.setPhoneNumber(contact.getPhoneNumber());
	    
	    return dto;
	}

	@Override
	public EmployeeContactDetailsResponseDto updateEmployeeContactDetails(Long employeeId,
			EmployeeContactDetailsRequestDto patchDto) {
		Employee employee = employeeRepository.findById(employeeId)
	            .orElseThrow(() -> new ResourceNotFoundException("No employee found with this employee ID: " + employeeId));
	    if (!employee.getIsActive()) {
	        throw new ResourceNotFoundException("Employee with ID " + employeeId + " is no longer active.");
	    }
	    EmployeeContactDetails contact = contactRepository.findByEmployeeId(employee)
	            .orElseThrow(() -> new ResourceNotFoundException("No contact details found for employee with ID: "));
	    logger.info("Contact details of employee");

		if (patchDto.getAlternateMobileNumber() != null) {
			contact.setAlternateMobileNumber(patchDto.getAlternateMobileNumber());
		}
		if (patchDto.getEmail() != null) {
			contact.setEmail(patchDto.getEmail());
		}
		if (patchDto.getEmergencyContact() != null) {
			contact.setEmergencyContact(patchDto.getEmergencyContact());
		}
		if (patchDto.getEmergencyContactRelation() != null) {
			contact.setEmergencyContactRelation(patchDto.getEmergencyContactRelation());
		}
		if (patchDto.getOfficeEmail() != null) {
			contact.setOfficeEmail(patchDto.getOfficeEmail());
		}
		if (patchDto.getPersonalPhone() != null) {
			contact.setPhoneNumber(patchDto.getPersonalPhone());
		}

		EmployeeContactDetails savedContact = contactRepository.save(contact);
		return mapper.map(savedContact, EmployeeContactDetailsResponseDto.class);
	}

	@Override
	public ProfileResponseDto getProfileDetails(Long employeeId) {
		Employee employee = employeeRepository.findById(employeeId)
	            .orElseThrow(() -> new ResourceNotFoundException("No employee found with this employee ID: " + employeeId));
	    if (!employee.getIsActive()) {
	        throw new ResourceNotFoundException("Employee with ID " + employeeId + " is no longer active.");
	    }
	    
	    logger.info("Viewing employee's profile details of employee");
	    return EmployeeToEmployeeResponseDto(employee);
	}

	private ProfileResponseDto EmployeeToEmployeeResponseDto(Employee employee) {
		if (employee == null) {
	        return null;
	    }

		ProfileResponseDto dto = new ProfileResponseDto();
	    
	    dto.setAadharNumber(employee.getAadharNumber());
	    dto.setBloodGroup(employee.getBloodGroup());
	    dto.setDateOfBirth(employee.getDateOfBirth());
	    dto.setFirstName(employee.getFirstName());
	    dto.setGender(employee.getGender());
	    dto.setLastName(employee.getLastName());
	    dto.setMiddleName(employee.getMiddleName());
	    dto.setNationality(employee.getNationality());
	    dto.setPanNumber(employee.getPanNumber());
	    dto.setSalutation(employee.getSalutation());
	    dto.setSpouse(employee.getSpouse());
	    dto.setProfilePhotoUrl(employee.getProfilePhotoUrl());
	    dto.setSalary(employee.getSalary());
	    
	    return dto;
	}

	@Override
	public ProfileResponseDto updateProfileDetails(Long employeeId, ProfileRequestDto patchDto, MultipartFile profilePhoto) {
		Employee employee = employeeRepository.findById(employeeId)
	            .orElseThrow(() -> new ResourceNotFoundException("No employee found with this employee ID: " + employeeId));
	    if (!employee.getIsActive()) {
	        throw new ResourceNotFoundException("Employee with ID " + employeeId + " is no longer active.");
	    }
	   

	    logger.info("Profile details of employee");
	    if (patchDto != null) {
		if (patchDto.getAadharNumber() != null) {
			employee.setAadharNumber(patchDto.getAadharNumber());
		}
		if (patchDto.getBloodGroup() != null) {
			employee.setBloodGroup(patchDto.getBloodGroup());
		}
		if (patchDto.getDateOfBirth() != null) {
			employee.setDateOfBirth(patchDto.getDateOfBirth());
		}
		if (patchDto.getFirstName() != null) {
			employee.setFirstName(patchDto.getFirstName());
		}
		if (patchDto.getGender() != null) {
			employee.setGender(patchDto.getGender());
		}
		if (patchDto.getLastName() != null) {
			employee.setLastName(patchDto.getLastName());
		}
		if (patchDto.getMiddleName() != null) {
			employee.setMiddleName(patchDto.getMiddleName());
		}
		if (patchDto.getNationality() != null) {
			employee.setNationality(patchDto.getNationality());
		}
		if (patchDto.getPanNumber() != null) {
			employee.setPanNumber(patchDto.getPanNumber());
		}
		if (patchDto.getSalutation() != null) {
			employee.setSalutation(patchDto.getSalutation());
		}
		if (patchDto.getSpouse() != null) {
			employee.setSpouse(patchDto.getSpouse());
		}
		if (patchDto.getSalary() != null) {
			employee.setSalary(patchDto.getSalary());
		}
//		if (patchDto.getProfilePhotoUrl() != null) {
//			employee.setProfilePhotoUrl(patchDto.getProfilePhotoUrl());
//		}
	    }
		 if (profilePhoto != null && !profilePhoto.isEmpty()) {
		        String uploadedUrl = cloudinaryService.uploadFile(profilePhoto);
		        System.out.println("Uploaded photo URL: " + uploadedUrl);
		        employee.setProfilePhotoUrl(uploadedUrl);
		    }

		Employee savedProfile = employeeRepository.save(employee);
		return mapper.map(savedProfile, ProfileResponseDto.class);
	}

}
