package com.aurionpro.payrollsystem.dto.organizationSideTicketFunctioning;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class OrganizationAdminTicketResponseDto {

	private Long ticketId;
	private Long organizationId;
	private String response;


}
