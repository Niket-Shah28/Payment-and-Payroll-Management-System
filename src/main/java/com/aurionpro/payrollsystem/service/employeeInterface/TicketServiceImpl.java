package com.aurionpro.payrollsystem.service.employeeInterface;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aurionpro.payrollsystem.dto.employeeRaiseTicket.*;
import com.aurionpro.payrollsystem.dto.organizationSideTicketFunctioning.*;
import com.aurionpro.payrollsystem.entity.employee.Employee;
import com.aurionpro.payrollsystem.entity.organization.Organization;
import com.aurionpro.payrollsystem.entity.ticket.*;
import com.aurionpro.payrollsystem.exception.ResourceNotFoundException;
import com.aurionpro.payrollsystem.repository.*;

@Service
public class TicketServiceImpl implements TicketService{

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketResponseRepository ticketResponseRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    
    // EMPLOYEE-SIDE METHODS
    

    @Transactional
    public TicketDto createTicket(TicketCreateDto createDto, Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));

        Organization organization = employee.getOrganization();
        if (organization == null) {
            throw new RuntimeException("Employee does not belong to any organization");
        }

        Ticket ticket = new Ticket();
        ticket.setQuery(createDto.getQuery());
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        ticket.setEmployee(employee);
        ticket.setOrganization(organization);

        Ticket savedTicket = ticketRepository.save(ticket);
        return convertToDto(savedTicket);
    }

    @Transactional(readOnly = true)
    public List<TicketSummaryDto> getAllTicketsByEmployeeId(Long employeeId) {
        List<Ticket> tickets = ticketRepository.findByEmployee_EmployeeIdOrderByCreatedAtDesc(employeeId);
        return tickets.stream().map(this::convertToSummaryDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TicketDto getTicketWithResponses(Long ticketId) {
        Ticket ticket = ticketRepository.findByIdWithDetails(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + ticketId));

        List<TicketResponse> responses = ticketResponseRepository.findByTicketIdWithDetails(ticketId);
        TicketDto ticketDto = convertToDto(ticket);

        ticketDto.setResponses(responses.stream().map(this::convertResponseToDto).collect(Collectors.toList()));
        return ticketDto;
    }

    @Transactional(readOnly = true)
    public TicketDto getTicketById(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + ticketId));
        return convertToDto(ticket);
    }

    @Transactional(readOnly = true)
    public List<TicketSummaryDto> getOpenTicketsByEmployeeId(Long employeeId) {
        List<Ticket> tickets = ticketRepository.findByEmployee_EmployeeIdAndStatus(employeeId, TicketStatus.OPEN);
        return tickets.stream().map(this::convertToSummaryDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TicketSummaryDto> getClosedTicketsByEmployeeId(Long employeeId) {
        List<Ticket> tickets = ticketRepository.findByEmployee_EmployeeIdAndStatus(employeeId, TicketStatus.CLOSE);
        return tickets.stream().map(this::convertToSummaryDto).collect(Collectors.toList());
    }

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

    @Transactional
    public TicketResponseDto giveReplyToTicketResponse(TicketResponseDto responseDto, Long employeeId) {
        Ticket ticket = ticketRepository.findById(responseDto.getTicketId())
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with ID: " + responseDto.getTicketId()));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));

        TicketResponse response = new TicketResponse();
        response.setResponse(responseDto.getResponse());
        response.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        response.setTicketId(ticket);
        response.setEmployeeId(employee);
        response.setOrganizationId(null);

        TicketResponse savedResponse = ticketResponseRepository.save(response);

        TicketResponseDto dto = new TicketResponseDto();
        dto.setResponseId(savedResponse.getResponseId());
        dto.setResponse(savedResponse.getResponse());
        dto.setCreatedAt(savedResponse.getCreatedAt());
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setOrganizationId(null);
        dto.setTicketId(ticket.getTicketId());
        return dto;
    }

 
    // ORGANIZATION / ADMIN-SIDE METHODS
   

    @Transactional(readOnly = true)
    public List<TicketDto> getAllTicketsWithFilters(OrganizationAdminTicketFilterDto filterDto) {
        Timestamp createdAfter = parseTimestamp(filterDto.getCreatedAfter());
        Timestamp createdBefore = parseTimestamp(filterDto.getCreatedBefore());

        List<Ticket> tickets = ticketRepository.findTicketsWithFilters(
                filterDto.getEmployeeId(),
                filterDto.getTicketId(),
                filterDto.getStatus(),
                createdAfter,
                createdBefore
        );

        return tickets.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    public TicketDto respondToTicket(OrganizationAdminTicketResponseDto responseDto, Long organizationId) {
        Ticket ticket = ticketRepository.findById(responseDto.getTicketId())
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with ID: " + responseDto.getTicketId()));

        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with ID: " + organizationId));

        TicketResponse response = new TicketResponse();
        response.setResponse(responseDto.getResponse());
        response.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        response.setTicketId(ticket);
        response.setOrganizationId(organization);
        response.setEmployeeId(null);

        ticketResponseRepository.save(response);
        return convertToDto(ticket);
    }

    @Transactional
    public TicketDto closeTicket(OrganizationAdminTicketCloseDto closeDto) {
        Ticket ticket = ticketRepository.findById(closeDto.getTicketId())
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with ID: " + closeDto.getTicketId()));

        if (!ticket.getOrganization().getOrganizationId().equals(closeDto.getOrganizationId())) {
            throw new RuntimeException("Unauthorized: Ticket does not belong to this organization.");
        }

        ticket.setStatus(TicketStatus.CLOSE);
        ticket.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        Ticket updatedTicket = ticketRepository.save(ticket);
        return convertToDto(updatedTicket);
    }

    // PRIVATE UTIL METHODS

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

        if (ticket.getEmployee() != null)
            dto.setEmployeeId(ticket.getEmployee().getEmployeeId());

        if (ticket.getOrganization() != null)
            dto.setOrganizationId(ticket.getOrganization().getOrganizationId());

        if (ticket.getResponses() != null && !ticket.getResponses().isEmpty()) {
            dto.setResponses(ticket.getResponses().stream()
                    .map(this::convertResponseToDto)
                    .collect(Collectors.toList()));
        } else {
            dto.setResponses(Collections.emptyList());
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

        Long responseCount = ticketResponseRepository.countByTicketId_TicketId(ticket.getTicketId());
        dto.setResponseCount(responseCount != null ? responseCount.intValue() : 0);

        return dto;
    }

    private TicketResponseDto convertResponseToDto(TicketResponse response) {
        TicketResponseDto dto = new TicketResponseDto();
        dto.setResponseId(response.getResponseId());
        dto.setResponse(response.getResponse());
        dto.setCreatedAt(response.getCreatedAt());
        dto.setTicketId(response.getTicketId().getTicketId());

        dto.setEmployeeId(Optional.ofNullable(response.getEmployeeId())
                .map(Employee::getEmployeeId)
                .orElse(null));

        dto.setOrganizationId(Optional.ofNullable(response.getOrganizationId())
                .map(Organization::getOrganizationId)
                .orElse(null));

        return dto;
    }
}
