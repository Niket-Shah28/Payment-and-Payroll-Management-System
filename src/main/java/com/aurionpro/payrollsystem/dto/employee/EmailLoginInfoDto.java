package com.aurionpro.payrollsystem.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class EmailLoginInfoDto {
	private String referenceId;
	private String email;
	private String name;
}
