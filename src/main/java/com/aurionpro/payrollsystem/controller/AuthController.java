package com.aurionpro.payrollsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.payrollsystem.dto.authentication.LoginRequestDto;
import com.aurionpro.payrollsystem.dto.authentication.LoginResponseDto;
import com.aurionpro.payrollsystem.service.authentication.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthService authService;
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@Valid LoginRequestDto dto){
		return ResponseEntity.ok(authService.login(dto));
	}
}
