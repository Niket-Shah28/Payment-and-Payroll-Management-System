package com.aurionpro.payrollsystem.dto.employeeRaiseTicket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class TicketCreateDto {

	@NotBlank(message = "Query cannot be blank")
	@Size(min = 10, max = 1000, message = "Query must be between 10 and 1000 characters")
	private String query;

	@NotNull(message = "Employee ID is required")
	private Long employeeId;

	@NotNull(message = "Organization ID is required")
	private Long organizationId;

}
