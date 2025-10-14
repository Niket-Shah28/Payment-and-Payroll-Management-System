package com.aurionpro.payrollsystem.dto.organizationSideTicketFunctioning;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class OrganizationAdminTicketResponseDto {

	private Long ticketId;
	private Long organizationId;
	private Long employeeId;
	private String response;
	//private Timestamp createdAt;

}
