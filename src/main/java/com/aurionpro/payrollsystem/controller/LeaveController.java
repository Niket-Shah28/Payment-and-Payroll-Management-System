package com.aurionpro.payrollsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.payrollsystem.dto.employeeAttendanceLeave.LeaveApplyRequestDto;
import com.aurionpro.payrollsystem.dto.employeeAttendanceLeave.LeaveDecisionDto;
import com.aurionpro.payrollsystem.dto.employeeAttendanceLeave.LeaveResponseDto;
import com.aurionpro.payrollsystem.service.employeeInterface.LeaveService;

@RestController
@RequestMapping("/employee/leave")
public class LeaveController {

	@Autowired
	private LeaveService leaveService;

	  @PostMapping("/apply")
	  @PreAuthorize("hasRole('EMPLOYEE')")
	    public ResponseEntity<LeaveResponseDto> applyLeave(
	    		Authentication authentication,
	            @RequestBody LeaveApplyRequestDto dto) {
		  Long employeeId = (Long) authentication.getDetails();
	        LeaveResponseDto response = leaveService.applyLeave(employeeId,dto);
	        return ResponseEntity.ok(response);
	    }

	  
	    @GetMapping("/view")
	    @PreAuthorize("hasRole('EMPLOYEE')")
	    public ResponseEntity<List<LeaveResponseDto>> getLeavesForEmployee(Authentication authentication) {
	    	Long employeeId = (Long) authentication.getDetails();
	        List<LeaveResponseDto> leaves = leaveService.getLeavesForEmployee(employeeId);
	        return ResponseEntity.ok(leaves);
	    }

	  
	    @GetMapping("/view/manager/{managerId}")
	    @PreAuthorize("hasRole('EMPLOYEE')")
	    public ResponseEntity<List<LeaveResponseDto>> getLeavesForManager(@PathVariable Long managerId) {
	        List<LeaveResponseDto> leaves = leaveService.getLeavesForManager(managerId);
	        return ResponseEntity.ok(leaves);
	    }

	    
	    @PutMapping("/decide/manager/{managerId}")
	    @PreAuthorize("hasRole('EMPLOYEE')")
	    public ResponseEntity<LeaveResponseDto> decideLeave(
	            @PathVariable Long managerId,
	            @RequestBody LeaveDecisionDto dto) {
	        LeaveResponseDto response = leaveService.decideLeave(dto, managerId);
	        return ResponseEntity.ok(response);
	    }


}
