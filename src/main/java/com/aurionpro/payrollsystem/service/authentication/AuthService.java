package com.aurionpro.payrollsystem.service.authentication;

import com.aurionpro.payrollsystem.dto.authentication.LoginRequestDto;
import com.aurionpro.payrollsystem.dto.authentication.LoginResponseDto;

public interface AuthService {
	public LoginResponseDto login(LoginRequestDto dto);
}
