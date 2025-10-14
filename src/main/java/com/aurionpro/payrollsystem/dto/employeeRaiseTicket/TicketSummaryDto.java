package com.aurionpro.payrollsystem.dto.employeeRaiseTicket;

import java.sql.Timestamp;

import com.aurionpro.payrollsystem.entity.ticket.TicketStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class TicketSummaryDto {
	
	 private Long ticketId;
	    private String query;
	    private Timestamp createdAt;
	    private Timestamp updatedAt;
	    private TicketStatus status;
	    private Long employeeId;
	    private String employeeName;
	    private Long organizationId;
	    private String organizationName;
	    private Integer responseCount;

}
