package com.aurionpro.payrollsystem.dto.authentication;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LoginRequestDto {
	@NotBlank(message = "ReferenceId is required")
	private String referenceId;
	
	@NotBlank(message = "Password is required")
	private String password;
}
