package com.aurionpro.payrollsystem.service.employeeInterface;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aurionpro.payrollsystem.dto.employeeRaiseTicket.TicketCreateDto;
import com.aurionpro.payrollsystem.dto.employeeRaiseTicket.TicketDto;
import com.aurionpro.payrollsystem.dto.employeeRaiseTicket.TicketResponseDto;
import com.aurionpro.payrollsystem.dto.employeeRaiseTicket.TicketSummaryDto;
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
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private TicketResponseRepository ticketResponseRepository;

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private OrganizationRepository organizationRepository;
	
	
	@Override
	@Transactional
	public TicketDto createTicket(TicketCreateDto createDto) {
		Ticket ticket = new Ticket();
	    ticket.setQuery(createDto.getQuery());
	    ticket.setStatus(TicketStatus.OPEN);
	    ticket.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));

	  
	    Employee employee = employeeRepository.findById(createDto.getEmployeeId())
	        .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + createDto.getEmployeeId()));
	    ticket.setEmployee(employee);

	    
	    Organization organization = organizationRepository.findById(createDto.getOrganizationId())
	        .orElseThrow(() -> new ResourceNotFoundException("Organization not found with ID: " + createDto.getOrganizationId()));
	    ticket.setOrganization(organization);

	    Ticket savedTicket = ticketRepository.save(ticket);
	    return convertToDto(savedTicket);
	}

	@Override
	public List<TicketSummaryDto> getAllTicketsByEmployeeId(Long employeeId) {
		List<Ticket> tickets = ticketRepository.findByEmployee_EmployeeIdOrderByCreatedAtDesc(employeeId);
		return tickets.stream().map(this::convertToSummaryDto).collect(Collectors.toList());
	}

	@Override
	public TicketDto getTicketWithResponses(Long ticketId) {
		Ticket ticket = ticketRepository.findByIdWithDetails(ticketId)
				.orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));

		List<TicketResponse> responses = ticketResponseRepository.findByTicketIdWithDetails(ticketId);

		TicketDto ticketDto = convertToDto(ticket);
		ticketDto.setResponses(responses.stream().map(this::convertResponseToDto).collect(Collectors.toList()));

		return ticketDto;
	}

	@Override
	public TicketDto getTicketById(Long ticketId) {
		Ticket ticket = ticketRepository.findById(ticketId)
				.orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));
		return convertToDto(ticket);
	}

	@Override
	public List<TicketSummaryDto> getOpenTicketsByEmployeeId(Long employeeId) {
		List<Ticket> tickets = ticketRepository.findByEmployee_EmployeeIdAndStatus(employeeId, TicketStatus.OPEN);
		return tickets.stream().map(this::convertToSummaryDto).collect(Collectors.toList());
	}

	@Override
	public List<TicketSummaryDto> getClosedTicketsByEmployeeId(Long employeeId) {
		List<Ticket> tickets = ticketRepository.findByEmployee_EmployeeIdAndStatus(employeeId, TicketStatus.CLOSE);
		return tickets.stream().map(this::convertToSummaryDto).collect(Collectors.toList());
	}

	private TicketDto convertToDto(Ticket ticket) {
		TicketDto dto = new TicketDto();
		dto.setTicketId(ticket.getTicketId());
		dto.setQuery(ticket.getQuery());
		dto.setCreatedAt(ticket.getCreatedAt());
		dto.setUpdatedAt(ticket.getUpdatedAt());
		dto.setStatus(ticket.getStatus());

		if (ticket.getResponses() != null && !ticket.getResponses().isEmpty()) {
	        List<TicketResponseDto> responseDtos = ticket.getResponses().stream()
	            .map(response -> {
	                TicketResponseDto rDto = new TicketResponseDto();
	                rDto.setResponseId(response.getResponseId());
	                rDto.setResponse(response.getResponse());
	                rDto.setCreatedAt(response.getCreatedAt());
	                rDto.setEmployeeId(response.getEmployeeId().getEmployeeId());
	                rDto.setOrganizationId(response.getOrganizationId().getOrganizationId());
	                rDto.setTicketId(response.getTicketId().getTicketId());
	                return rDto;
	            })
	            .collect(Collectors.toList());
	        dto.setResponses(responseDtos);
	    } else {
	        dto.setResponses(Collections.emptyList());
	    }
		
		if (ticket.getEmployee() != null) {
			dto.setEmployeeId(ticket.getEmployee().getEmployeeId());
			
		}

		if (ticket.getOrganization() != null) {
			dto.setOrganizationId(ticket.getOrganization().getOrganizationId());
			
		}

		return dto;
	}

	private TicketSummaryDto convertToSummaryDto(Ticket ticket) {
		TicketSummaryDto dto = new TicketSummaryDto();
		dto.setTicketId(ticket.getTicketId());
		dto.setQuery(ticket.getQuery());
		dto.setCreatedAt(ticket.getCreatedAt());
		dto.setUpdatedAt(ticket.getUpdatedAt());
		dto.setStatus(ticket.getStatus());

		if (ticket.getEmployee() != null) {
			dto.setEmployeeId(ticket.getEmployee().getEmployeeId());
			dto.setEmployeeName(ticket.getEmployee().getFirstName() + " " + ticket.getEmployee().getLastName());
		}

		if (ticket.getOrganization() != null) {
			dto.setOrganizationId(ticket.getOrganization().getOrganizationId());
			dto.setOrganizationName(ticket.getOrganization().getOrganizationName());
		}

		// Get response count
		Long responseCount = ticketResponseRepository.countByTicketId_TicketId(ticket.getTicketId());
		dto.setResponseCount(responseCount != null ? responseCount.intValue() : 0);

		return dto;
	}

	private TicketResponseDto convertResponseToDto(TicketResponse response) {
		TicketResponseDto dto = new TicketResponseDto();
//		dto.setResponseId(response.getResponseId());
		dto.setResponse(response.getResponse());
		dto.setCreatedAt(response.getCreatedAt());

		if (response.getEmployeeId() != null) {
			dto.setEmployeeId(response.getEmployeeId().getEmployeeId());
		}

		if (response.getOrganizationId() != null) {
			dto.setOrganizationId(response.getOrganizationId().getOrganizationId());
		}

		if (response.getTicketId() != null) {
			dto.setTicketId(response.getTicketId().getTicketId());
		}

		return dto;
	}

	@Override
	public TicketResponseDto giveReplyToTicketResponse(TicketResponseDto responseDto) {
		Ticket ticket = ticketRepository.findById(responseDto.getTicketId())
	            .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with ID: " + responseDto.getTicketId()));

	    // Validate employee
	    Employee employee = employeeRepository.findById(responseDto.getEmployeeId())
	            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + responseDto.getEmployeeId()));

	    // Validate organization
	    Organization organization = organizationRepository.findById(responseDto.getOrganizationId())
	            .orElseThrow(() -> new ResourceNotFoundException("Organization not found with ID: " + responseDto.getOrganizationId()));

	    // Create response entity
	    TicketResponse response = new TicketResponse();
	    response.setResponse(responseDto.getResponse());
	    response.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
	    response.setEmployeeId(employee);
	    response.setOrganizationId(organization);
	    response.setTicketId(ticket);

	    TicketResponse savedResponse = ticketResponseRepository.save(response);

	    // Convert to DTO and return
//	    TicketResponseDto dto = new TicketResponseDto();
	    responseDto.setResponse(savedResponse.getResponse());
	    responseDto.setCreatedAt(savedResponse.getCreatedAt());
	    responseDto.setEmployeeId(employee.getEmployeeId());
	    responseDto.setOrganizationId(organization.getOrganizationId());
	    responseDto.setTicketId(ticket.getTicketId());

	    return responseDto;
	}
	
	
	@Override
	@Transactional
	public TicketDto updateTicket(Long ticketId, String updatedQuery) {

	    Ticket ticket = ticketRepository.findById(ticketId)
	            .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with ID: " + ticketId));

	    if (ticket.getStatus() == TicketStatus.CLOSE) {
	        throw new RuntimeException("Cannot update a closed ticket.");
	    }

	    if (updatedQuery != null && !updatedQuery.trim().isEmpty()) {
	        ticket.setQuery(updatedQuery);
	        ticket.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
	    }
	    
	    

	    Ticket updatedTicket = ticketRepository.save(ticket);
	    return convertToDto(updatedTicket);
	}


}
