package com.aurionpro.payrollsystem.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class LoginResponseDto {
	private String accessToken;
	private String tokenType="Bearer";
}
