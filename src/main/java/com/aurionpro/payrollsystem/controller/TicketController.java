package com.aurionpro.payrollsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.aurionpro.payrollsystem.dto.employeeRaiseTicket.TicketCreateDto;
import com.aurionpro.payrollsystem.dto.employeeRaiseTicket.TicketDto;
import com.aurionpro.payrollsystem.dto.employeeRaiseTicket.TicketResponseDto;
import com.aurionpro.payrollsystem.dto.employeeRaiseTicket.TicketSummaryDto;
import com.aurionpro.payrollsystem.dto.organizationSideTicketFunctioning.OrganizationAdminTicketCloseDto;
import com.aurionpro.payrollsystem.dto.organizationSideTicketFunctioning.OrganizationAdminTicketFilterDto;
import com.aurionpro.payrollsystem.dto.organizationSideTicketFunctioning.OrganizationAdminTicketResponseDto;
import com.aurionpro.payrollsystem.service.employeeInterface.TicketServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tickets")
@CrossOrigin(origins = "http://localhost:4200")
public class TicketController {

    @Autowired
    private TicketServiceImpl ticketService;

   
    // EMPLOYEE ENDPOINTS
   

    @PostMapping("/raise")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<TicketDto> createTicket(@Valid @RequestBody TicketCreateDto createDto,
                                                  Authentication authentication) {
        Long employeeId = (Long) authentication.getDetails();
        TicketDto createdTicket = ticketService.createTicket(createDto, employeeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<TicketSummaryDto>> getAllTicketsByEmployee(Authentication authentication) {
        Long employeeId = (Long) authentication.getDetails();
        List<TicketSummaryDto> tickets = ticketService.getAllTicketsByEmployeeId(employeeId);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/response/{ticketId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<TicketDto> getTicketWithResponses(@PathVariable Long ticketId) {
        TicketDto ticket = ticketService.getTicketWithResponses(ticketId);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/open")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<TicketSummaryDto>> getOpenTickets(Authentication authentication) {
        Long employeeId = (Long) authentication.getDetails();
        List<TicketSummaryDto> tickets = ticketService.getOpenTicketsByEmployeeId(employeeId);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/closed")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<TicketSummaryDto>> getClosedTickets(Authentication authentication) {
        Long employeeId = (Long) authentication.getDetails();
        List<TicketSummaryDto> tickets = ticketService.getClosedTicketsByEmployeeId(employeeId);
        return ResponseEntity.ok(tickets);
    }

    @PostMapping("/emp/respond")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<TicketResponseDto> replyToTicket(@Valid @RequestBody TicketResponseDto responseDto,
                                                           Authentication authentication) {
        Long employeeId = (Long) authentication.getDetails();
        TicketResponseDto response = ticketService.giveReplyToTicketResponse(responseDto, employeeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

  
    // ORGANIZATION ADMIN ENDPOINTS
   

    @GetMapping("/filter")
    @PreAuthorize("hasRole('ORG_ADMIN')")
    public ResponseEntity<List<TicketDto>> getTicketsWithFilters(@ModelAttribute OrganizationAdminTicketFilterDto filterDto) {
        List<TicketDto> tickets = ticketService.getAllTicketsWithFilters(filterDto);
        return ResponseEntity.ok(tickets);
    }

    @PostMapping("/org/respond")
    @PreAuthorize("hasRole('ORG_ADMIN')")
    public ResponseEntity<TicketDto> respondToTicket(@RequestBody OrganizationAdminTicketResponseDto responseDto,
                                                     Authentication authentication) {
        Long organizationId = (Long) authentication.getDetails();
        TicketDto updated = ticketService.respondToTicket(responseDto, organizationId);
        return ResponseEntity.status(HttpStatus.CREATED).body(updated);
    }

    @PatchMapping("/close")
    @PreAuthorize("hasRole('ORG_ADMIN')")
    public ResponseEntity<TicketDto> closeTicket(@RequestBody OrganizationAdminTicketCloseDto closeDto) {
        TicketDto updated = ticketService.closeTicket(closeDto);
        return ResponseEntity.ok(updated);
    }

   
    // GLOBAL EXCEPTION HANDLER (for this controller)
    

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Inner error response class
    @lombok.Data
    @lombok.AllArgsConstructor
    static class ErrorResponse {
        private int status;
        private String message;
        private long timestamp;
    }
}
