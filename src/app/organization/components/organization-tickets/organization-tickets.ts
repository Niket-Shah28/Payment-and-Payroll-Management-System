import { Component, OnInit } from '@angular/core';
import { TicketService } from '../../service/ticket-service'; 
import { TicketDto, TicketResponseDto } from '../../dto/ticket-dto';
import { TicketSummaryDto } from '../../dto/ticket-summary-dto';
import { OrganizationAdminTicketFilterDto } from '../../dto/organization-admin-ticket-filter-dto';
import { OrganizationAdminTicketResponseDto } from '../../dto/organization-admin-ticket-response-dto';
import { OrganizationAdminTicketCloseDto } from '../../dto/organization-admin-ticket-close-dto';
import { TicketStatus } from '../../dto/ticket-status'; 

@Component({
  selector: 'app-organization-tickets',
  standalone: false,
  templateUrl: './organization-tickets.html',
  styleUrl: './organization-tickets.css'
})
export class OrganizationTickets implements OnInit{
  tickets: TicketDto[] = [];
  selectedTicket: TicketDto | null = null;
  responseText: string = '';
  filter: OrganizationAdminTicketFilterDto = {};
  ticketStatusEnum = TicketStatus;

  constructor(private ticketService: TicketService) {}

  ngOnInit(): void {
    this.loadTickets();
  }

  loadTickets(): void {
    this.ticketService.getTicketsWithFilters(this.filter).subscribe({
      next: (data) => this.tickets = data,
      error: (err) => console.error(err)
    });
  }

  selectTicket(ticket: TicketDto): void {
    this.selectedTicket = ticket;
    this.responseText = '';
  }

  replyToTicket(): void {
    if (!this.selectedTicket || !this.responseText.trim()) return;
    const responseDto: OrganizationAdminTicketResponseDto = {
      ticketId: this.selectedTicket.ticketId,
      response: this.responseText
    };
    this.ticketService.respondToTicket(responseDto).subscribe({
      next: (updatedTicket) => {
        this.selectedTicket = updatedTicket;
        this.loadTickets(); // refresh table
        this.responseText = '';
      },
      error: (err) => console.error(err)
    });
  }

  closeTicket(ticket: TicketDto): void {
  const closeDto: OrganizationAdminTicketCloseDto = {
    ticketId: ticket.ticketId,
    organizationId: ticket.organizationId // make sure this exists in TicketDto
  };

  this.ticketService.closeTicket(closeDto).subscribe({
    next: (updatedTicket) => {
      ticket.status = updatedTicket.status; // update local status
    },
    error: (err) => console.error(err)
  });
}


  applyFilter(): void {
    this.loadTickets();
  }

  resetFilter(): void {
    this.filter = {};
    this.loadTickets();
  }
}
