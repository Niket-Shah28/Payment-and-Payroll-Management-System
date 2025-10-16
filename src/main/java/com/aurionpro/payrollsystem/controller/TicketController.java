package com.aurionpro.payrollsystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.payrollsystem.dto.employeeRaiseTicket.TicketCreateDto;
import com.aurionpro.payrollsystem.dto.employeeRaiseTicket.TicketDto;
import com.aurionpro.payrollsystem.dto.employeeRaiseTicket.TicketResponseDto;
import com.aurionpro.payrollsystem.dto.employeeRaiseTicket.TicketSummaryDto;
import com.aurionpro.payrollsystem.service.employeeInterface.TicketService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins="http://localhost:4200")
public class TicketController {

	@Autowired
	private TicketService ticketService;

	@PostMapping("/tickets")
	@PreAuthorize("hasRole('EMPLOYEE')")
	public ResponseEntity<TicketDto> createTicket(@Valid @RequestBody TicketCreateDto createDto,
	                                              Authentication authentication) {
	    
	    Long employeeId = (Long) authentication.getDetails();

	    
	    TicketDto createdTicket = ticketService.createTicket(createDto, employeeId);

	    return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
	}


	    
	   
	    @GetMapping("/tickets")
	    @PreAuthorize("hasRole('EMPLOYEE')")
	    public ResponseEntity<List<TicketSummaryDto>> getAllTicketsByEmployeeId( Authentication authentication) {
	    	Long employeeId = (Long) authentication.getDetails();
	        List<TicketSummaryDto> tickets = ticketService.getAllTicketsByEmployeeId(employeeId);
	        return ResponseEntity.ok(tickets);
	    }
	  
	    @GetMapping("/tickets/{ticketId}")
	    @PreAuthorize("hasRole('EMPLOYEE')")
	    public ResponseEntity<TicketDto> getTicketWithResponses(@PathVariable Long ticketId) {
	        TicketDto ticket = ticketService.getTicketWithResponses(ticketId);
	        return ResponseEntity.ok(ticket);
	    }
	    
	   
	    @GetMapping("/tickets/open")
	    @PreAuthorize("hasRole('EMPLOYEE')")
	    public ResponseEntity<List<TicketSummaryDto>> getOpenTicketsByEmployeeId(Authentication authentication) {
	    	Long employeeId = (Long) authentication.getDetails();
	        List<TicketSummaryDto> tickets = ticketService.getOpenTicketsByEmployeeId(employeeId);
	        return ResponseEntity.ok(tickets);
	    }
	    
	  
	    @GetMapping("/tickets/close")
	    @PreAuthorize("hasRole('EMPLOYEE')")
	    public ResponseEntity<List<TicketSummaryDto>> getClosedTicketsByEmployeeId(Authentication authentication) {
	    	Long employeeId = (Long) authentication.getDetails();
	        List<TicketSummaryDto> tickets = ticketService.getClosedTicketsByEmployeeId(employeeId);
	        return ResponseEntity.ok(tickets);
	    }
	    
	    //Add post request of employee giving reply to org admin response
	    
	    /**
	     * Exception handler for this controller
	     */
	    @ExceptionHandler(RuntimeException.class)
	    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
	        ErrorResponse error = new ErrorResponse(
	            HttpStatus.BAD_REQUEST.value(),
	            ex.getMessage(),
	            System.currentTimeMillis()
	        );
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	    }
	    
	    @PostMapping("/tickets/response")
	    @PreAuthorize("hasRole('EMPLOYEE')")
	    public ResponseEntity<TicketResponseDto> giveReplyToTicketResponse(@Valid @RequestBody TicketResponseDto responseDto, Authentication authentication) {
	    	Long employeeId = (Long) authentication.getDetails();
	        TicketResponseDto response = ticketService.giveReplyToTicketResponse(responseDto,employeeId);
	        return ResponseEntity.status(HttpStatus.CREATED).body(response);
	    }
	    
	    @PatchMapping("/tickets/{ticketId}")
	    @PreAuthorize("hasRole('EMPLOYEE')")
	    public ResponseEntity<TicketDto> updateTicket(
	            @PathVariable Long ticketId,
	            @RequestBody Map<String, String> updates) {

	        String newQuery = updates.get("query");
	        TicketDto updatedTicket = ticketService.updateTicket(ticketId, newQuery);
	        return ResponseEntity.ok(updatedTicket);
	    }
	    
	    /**
	     * Inner class for error response
	     */
	    @lombok.Data
	    @lombok.AllArgsConstructor
	    static class ErrorResponse {
	        private int status;
	        private String message;
	        private long timestamp;
	    }
	
}
