package com.aurionpro.payrollsystem.dto.organizationSideTicketFunctioning;

import com.aurionpro.payrollsystem.entity.ticket.TicketStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class OrganizationAdminTicketFilterDto {

	private Long employeeId;
	private Long ticketId;
	private TicketStatus status;
	private String createdAfter;
	private String createdBefore;

}
