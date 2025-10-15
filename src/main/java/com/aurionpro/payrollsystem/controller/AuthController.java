package com.aurionpro.payrollsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.payrollsystem.dto.authentication.LoginRequestDto;
import com.aurionpro.payrollsystem.dto.authentication.LoginResponseDto;
import com.aurionpro.payrollsystem.exception.CapatchaFailedException;
import com.aurionpro.payrollsystem.service.authentication.AuthService;
import com.aurionpro.payrollsystem.service.authentication.CapatchaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins="*")
public class AuthController {
	@Autowired
	private AuthService authService;
	
	@Autowired
	private CapatchaService captchaService;
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto dto){
		
		boolean isCaptchaValid = captchaService.verifyCaptcha(dto.getCapatchaResponse());
		
		if (!isCaptchaValid) {
            throw new CapatchaFailedException("Capatcha Failed", HttpStatus.FORBIDDEN);
        }
		
		return ResponseEntity.ok(authService.login(dto));
	}
}
