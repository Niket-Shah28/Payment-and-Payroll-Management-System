package com.aurionpro.payrollsystem.service.organizationTicket;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aurionpro.payrollsystem.dto.employeeRaiseTicket.TicketDto;
import com.aurionpro.payrollsystem.dto.employeeRaiseTicket.TicketResponseDto;
import com.aurionpro.payrollsystem.dto.organizationSideTicketFunctioning.OrganizationAdminTicketCloseDto;
import com.aurionpro.payrollsystem.dto.organizationSideTicketFunctioning.OrganizationAdminTicketFilterDto;
import com.aurionpro.payrollsystem.dto.organizationSideTicketFunctioning.OrganizationAdminTicketResponseDto;
import com.aurionpro.payrollsystem.entity.employee.Employee;
import com.aurionpro.payrollsystem.entity.organization.Organization;
import com.aurionpro.payrollsystem.entity.ticket.Ticket;
import com.aurionpro.payrollsystem.entity.ticket.TicketResponse;
import com.aurionpro.payrollsystem.entity.ticket.TicketStatus;
import com.aurionpro.payrollsystem.exception.ResourceNotFoundException;
import com.aurionpro.payrollsystem.repository.EmployeeRepository;
import com.aurionpro.payrollsystem.repository.OrganizationRepository;
import com.aurionpro.payrollsystem.repository.TicketRepository;
import com.aurionpro.payrollsystem.repository.TicketResponseRepository;

@Service
public class OrganizationTicketServiceImpl implements OrganizationTicketService{

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private TicketResponseRepository ticketResponseRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private OrganizationRepository organizationRepository;

	@Override
	@Transactional(readOnly = true)
	public List<TicketDto> getAllTicketsWithFilters(OrganizationAdminTicketFilterDto filterDto) {
		Timestamp createdAfter = parseTimestamp(filterDto.getCreatedAfter());
		Timestamp createdBefore = parseTimestamp(filterDto.getCreatedBefore());

		List<Ticket> tickets = ticketRepository.findTicketsWithFilters(filterDto.getEmployeeId(),
				filterDto.getTicketId(), filterDto.getStatus(), createdAfter, createdBefore);

		return tickets.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public TicketDto respondToTicket(OrganizationAdminTicketResponseDto responseDto) {
		Ticket ticket = ticketRepository.findById(responseDto.getTicketId()).orElseThrow(
				() -> new ResourceNotFoundException("Ticket not found with ID: " + responseDto.getTicketId()));

		Organization org = organizationRepository.findById(responseDto.getOrganizationId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Organization not found with ID: " + responseDto.getOrganizationId()));

		Employee employee = employeeRepository.findById(responseDto.getEmployeeId()).orElseThrow(
				() -> new ResourceNotFoundException("Employee not found with ID: " + responseDto.getEmployeeId()));

		TicketResponse response = new TicketResponse();
		response.setResponse(responseDto.getResponse());
		response.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		response.setTicketId(ticket);
		response.setOrganizationId(org);
		response.setEmployeeId(employee);

		ticketResponseRepository.save(response);

		return convertToDto(ticket);
	}

	@Override
	@Transactional
	public TicketDto closeTicket(OrganizationAdminTicketCloseDto closeDto) {
		Ticket ticket = ticketRepository.findById(closeDto.getTicketId()).orElseThrow(
				() -> new ResourceNotFoundException("Ticket not found with ID: " + closeDto.getTicketId()));

		if (!ticket.getOrganization().getOrganizationId().equals(closeDto.getOrganizationId())) {
			throw new RuntimeException("Unauthorized: Ticket does not belong to this organization.");
		}

		ticket.setStatus(TicketStatus.CLOSE);
		ticket.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

		Ticket updatedTicket = ticketRepository.save(ticket);
		return convertToDto(updatedTicket);
	}

	    private Timestamp parseTimestamp(String isoDateTime) {
	        if (isoDateTime == null || isoDateTime.isEmpty()) return null;
	        try {
	            return Timestamp.valueOf(LocalDateTime.parse(isoDateTime));
	        } catch (DateTimeParseException e) {
	            return null;
	        }
	    }

	private TicketDto convertToDto(Ticket ticket) {
		TicketDto dto = new TicketDto();
		dto.setTicketId(ticket.getTicketId());
		dto.setQuery(ticket.getQuery());
		dto.setCreatedAt(ticket.getCreatedAt());
		dto.setUpdatedAt(ticket.getUpdatedAt());
		dto.setStatus(ticket.getStatus());
		dto.setEmployeeId(ticket.getEmployee().getEmployeeId());
		dto.setOrganizationId(ticket.getOrganization().getOrganizationId());

		if (ticket.getResponses() != null) {
			dto.setResponses(ticket.getResponses().stream().map(r -> {
				TicketResponseDto rDto = new TicketResponseDto();
				rDto.setResponse(r.getResponse());
				rDto.setCreatedAt(r.getCreatedAt());
				rDto.setEmployeeId(r.getEmployeeId().getEmployeeId());
				rDto.setOrganizationId(r.getOrganizationId().getOrganizationId());
				rDto.setTicketId(r.getTicketId().getTicketId());
				return rDto;
			}).collect(Collectors.toList()));
		}

		return dto;
	}

}
