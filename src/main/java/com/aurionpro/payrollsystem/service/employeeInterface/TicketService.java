package com.aurionpro.payrollsystem.service.employeeInterface;

import java.util.List;

import com.aurionpro.payrollsystem.dto.employeeRaiseTicket.TicketCreateDto;
import com.aurionpro.payrollsystem.dto.employeeRaiseTicket.TicketDto;
import com.aurionpro.payrollsystem.dto.employeeRaiseTicket.TicketResponseDto;
import com.aurionpro.payrollsystem.dto.employeeRaiseTicket.TicketSummaryDto;
import com.aurionpro.payrollsystem.dto.organizationSideTicketFunctioning.OrganizationAdminTicketCloseDto;
import com.aurionpro.payrollsystem.dto.organizationSideTicketFunctioning.OrganizationAdminTicketFilterDto;
import com.aurionpro.payrollsystem.dto.organizationSideTicketFunctioning.OrganizationAdminTicketResponseDto;

public interface TicketService {

	TicketDto createTicket(TicketCreateDto createDto, Long employeeID);


	List<TicketSummaryDto> getAllTicketsByEmployeeId(Long employeeId);
	
	TicketResponseDto  giveReplyToTicketResponse (TicketResponseDto responseDto,  Long employeeId);
	
	TicketDto updateTicket(Long ticketId, String updatedQuery);

	
	TicketDto getTicketWithResponses(Long ticketId);

	
	TicketDto getTicketById(Long ticketId);

	
	List<TicketSummaryDto> getOpenTicketsByEmployeeId(Long employeeId);

	
	List<TicketSummaryDto> getClosedTicketsByEmployeeId(Long employeeId);
	
	List<TicketDto> getAllTicketsWithFilters(OrganizationAdminTicketFilterDto filterDto);

	TicketDto respondToTicket(OrganizationAdminTicketResponseDto responseDto, Long organizationId);

	TicketDto closeTicket(OrganizationAdminTicketCloseDto closeDto);

}
