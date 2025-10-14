package com.aurionpro.payrollsystem.controller;

import java.time.LocalDate;	
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.payrollsystem.dto.employeeAttendanceLeave.AttendanceMarkRequest;
import com.aurionpro.payrollsystem.dto.employeeAttendanceLeave.AttendanceResposeDto;
import com.aurionpro.payrollsystem.service.employeeInterface.AttendanceService;

@RestController
@RequestMapping("/employee")
public class AttendanceController {
	
	@Autowired
	private AttendanceService attendanceService;

    @PostMapping("/attendance/mark")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<AttendanceResposeDto>> markAttendance(@RequestBody AttendanceMarkRequest request, Authentication authentication) {
    	Long employeeId = (Long) authentication.getDetails();
        List<AttendanceResposeDto> saved = attendanceService.markAttendance(employeeId, request);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/attendance")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<AttendanceResposeDto>> getAttendance(
            Authentication authentication,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
    	 Long employeeId = (Long) authentication.getDetails();
        List<AttendanceResposeDto> list = attendanceService.getAttendanceForEmployee(employeeId, from, to);
        return ResponseEntity.ok(list);
    }

}
