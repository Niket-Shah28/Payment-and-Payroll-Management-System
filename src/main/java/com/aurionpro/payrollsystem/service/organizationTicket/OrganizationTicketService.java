package com.aurionpro.payrollsystem.service.organizationTicket;

import java.util.List;

import com.aurionpro.payrollsystem.dto.employeeRaiseTicket.TicketDto;
import com.aurionpro.payrollsystem.dto.organizationSideTicketFunctioning.OrganizationAdminTicketCloseDto;
import com.aurionpro.payrollsystem.dto.organizationSideTicketFunctioning.OrganizationAdminTicketFilterDto;
import com.aurionpro.payrollsystem.dto.organizationSideTicketFunctioning.OrganizationAdminTicketResponseDto;

public interface OrganizationTicketService {

	List<TicketDto> getAllTicketsWithFilters(OrganizationAdminTicketFilterDto filterDto);

	TicketDto respondToTicket(OrganizationAdminTicketResponseDto responseDto);

	TicketDto closeTicket(OrganizationAdminTicketCloseDto closeDto);

}
