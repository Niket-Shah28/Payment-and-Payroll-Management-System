package com.aurionpro.payrollsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import com.aurionpro.payrollsystem.dto.employeeAttendanceLeave.LeaveApplyRequestDto;
import com.aurionpro.payrollsystem.dto.employeeAttendanceLeave.LeaveDecisionDto;
import com.aurionpro.payrollsystem.dto.employeeAttendanceLeave.LeaveResponseDto;
import com.aurionpro.payrollsystem.service.employeeInterface.LeaveService;

@RestController
@RequestMapping("/employee/leave")
@CrossOrigin(origins="http://localhost:4200")
public class LeaveController {

	@Autowired
	private LeaveService leaveService;

	@PostMapping("/apply")
	@PreAuthorize("hasRole('EMPLOYEE')")
	public ResponseEntity<LeaveResponseDto> apply(Authentication authentication, @RequestBody Long managerId,
			@RequestBody LeaveApplyRequestDto dto) {
		Long employeeId = (Long) authentication.getDetails();
		LeaveResponseDto resp = leaveService.applyLeave(employeeId, managerId, dto);
		return ResponseEntity.ok(resp);
	}

	@GetMapping("/view")
	@PreAuthorize("hasRole('EMPLOYEE')")
	public ResponseEntity<List<LeaveResponseDto>> getEmployeeLeaves(Authentication authentication) {
		Long employeeId = (Long) authentication.getDetails();
		return ResponseEntity.ok(leaveService.getLeavesForEmployee(employeeId));
	}

	@GetMapping("/view/manager/{managerId}")
	@PreAuthorize("hasRole('EMPLOYEE')")
	public ResponseEntity<List<LeaveResponseDto>> getManagerLeaves(@PathVariable Long managerId) {
		return ResponseEntity.ok(leaveService.getLeavesForManager(managerId));
	}

	@PostMapping("/manager/decision")
	@PreAuthorize("hasRole('EMPLOYEE')")
	public ResponseEntity<LeaveResponseDto> decideLeave(@RequestBody LeaveDecisionDto dto) {
		Long managerId = dto.getEmployeeId();
		LeaveResponseDto resp = leaveService.decideLeave(dto, managerId);
		return ResponseEntity.ok(resp);
	}

}
