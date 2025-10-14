package com.aurionpro.payrollsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aurionpro.payrollsystem.dto.manageEmployeeProfile.EmployeeAddressRequestDto;
import com.aurionpro.payrollsystem.dto.manageEmployeeProfile.EmployeeAddressResponseDto;
import com.aurionpro.payrollsystem.dto.manageEmployeeProfile.EmployeeContactDetailsRequestDto;
import com.aurionpro.payrollsystem.dto.manageEmployeeProfile.EmployeeContactDetailsResponseDto;
import com.aurionpro.payrollsystem.dto.manageEmployeeProfile.ProfileRequestDto;
import com.aurionpro.payrollsystem.dto.manageEmployeeProfile.ProfileResponseDto;
import com.aurionpro.payrollsystem.service.employeeInterface.EmployeeManageProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employee")
public class EmployeeManageProfileController {

	@Autowired
	private EmployeeManageProfileService employeeService;
	

	@GetMapping("/address")
	@PreAuthorize("hasRole('EMPLOYEE')")
	public ResponseEntity<EmployeeAddressResponseDto> getEmployeeAddress(Authentication authentication) {

		Long employeeId = (Long) authentication.getDetails();
		EmployeeAddressResponseDto addressDto = employeeService.getEmployeeAddress(employeeId);

		return ResponseEntity.ok(addressDto);
	}

	@PatchMapping("/address")
	@PreAuthorize("hasRole('EMPLOYEE')")
	public ResponseEntity<EmployeeAddressResponseDto> updateAddress(
			@Valid @RequestBody EmployeeAddressRequestDto patchDto, Authentication authentication) {

		Long employeeId = (Long) authentication.getDetails();

		EmployeeAddressResponseDto responseDto = employeeService.updateEmployeeAddress(employeeId, patchDto);

		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/contactdetails")
	@PreAuthorize("hasRole('EMPLOYEE')")
	public ResponseEntity<EmployeeContactDetailsResponseDto> getEmployeeContactDetails(Authentication authentication){
		Long employeeId = (Long) authentication.getDetails();
		EmployeeContactDetailsResponseDto contactDto = employeeService.getEmployeeContactDetails(employeeId);
		
		return ResponseEntity.ok(contactDto);
	}
	
	@PatchMapping("/contactdetails")
	@PreAuthorize("hasRole('EMPLOYEE')")
	public ResponseEntity<EmployeeContactDetailsResponseDto> updateContactDetails(
			@Valid @RequestBody EmployeeContactDetailsRequestDto patchDto, Authentication authentication) {
		
		Long employeeId = (Long) authentication.getDetails();

		EmployeeContactDetailsResponseDto contactDto = employeeService.updateEmployeeContactDetails(employeeId, patchDto);

		return ResponseEntity.ok(contactDto);
		
	}
	
	@GetMapping("/profile")
	@PreAuthorize("hasRole('EMPLOYEE')")
	public ResponseEntity<ProfileResponseDto> getProfileDetails(Authentication authentication){
		Long employeeId = (Long) authentication.getDetails();
		ProfileResponseDto profileDto = employeeService.getProfileDetails(employeeId);
		
		return ResponseEntity.ok(profileDto);
	}
	
	@PatchMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('EMPLOYEE')")
	public ResponseEntity<ProfileResponseDto> updateProfileDetails(
	        @RequestPart(value="profileData", required=false) @Valid ProfileRequestDto patchDto,
	        @RequestPart(value = "profilePhoto", required = false) MultipartFile profilePhoto,
	        Authentication authentication) {

	    Long employeeId = (Long) authentication.getDetails();
	    ProfileResponseDto profileDto = employeeService.updateProfileDetails(employeeId, patchDto, profilePhoto);
	    return ResponseEntity.ok(profileDto);
	}

	
	
}
