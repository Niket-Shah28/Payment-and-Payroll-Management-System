import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TicketService } from '../../../services/ticket-service';
import { EmployeeService } from '../../../services/employee-service';
import { TicketSummaryDto } from '../../../dto/ticket-summary-dto';
import { TicketDto } from '../../../dto/ticket-dto';
import { TicketResponseDto } from '../../../dto/ticket-response-dto';
import { TicketCreateDto } from '../../../dto/ticket-create-dto';

@Component({
  selector: 'app-raise-ticket',
  standalone: false,
  templateUrl: './raise-ticket.html',
  styleUrls: ['./raise-ticket.css']
})
export class RaiseTicket implements OnInit {
  ticketForm!: FormGroup;
  tickets: TicketSummaryDto[] = [];
  filter: 'ALL' | 'OPEN' | 'CLOSE' = 'ALL';
  profile: any;

  selectedTicket?: TicketDto;
  replyText: string = '';

  constructor(
    private fb: FormBuilder,
    private ticketService: TicketService,
    private employeeService: EmployeeService
  ) {}

  ngOnInit(): void {
    this.ticketForm = this.fb.group({
      query: ['', [Validators.required, Validators.minLength(10)]]
    });

    // Load profile and tickets
    this.employeeService.getProfile().subscribe({
      next: (profile) => {
        this.profile = profile;
        this.loadTickets();
      },
      error: (err) => console.error('Error loading profile:', err)
    });
  }

  submitTicket(): void {
    if (this.ticketForm.invalid) return;

    const createDto: TicketCreateDto = {
      query: this.ticketForm.value.query
    };

    this.ticketService.createTicket(createDto).subscribe({
      next: () => {
        alert('✅ Ticket submitted successfully!');
        this.ticketForm.reset();
        this.loadTickets();
      },
      error: (err) => {
        console.error('Error creating ticket:', err);
        alert('❌ Failed to submit ticket.');
      }
    });
  }

  loadTickets(): void {
    const ticketObservable =
      this.filter === 'OPEN'
        ? this.ticketService.getOpenTickets()
        : this.filter === 'CLOSE'
        ? this.ticketService.getClosedTickets()
        : this.ticketService.getAllTickets();

    ticketObservable.subscribe({
      next: (tickets) => (this.tickets = tickets),
      error: (err) => console.error('Error fetching tickets:', err)
    });
  }

  setFilter(filter: 'ALL' | 'OPEN' | 'CLOSE'): void {
    this.filter = filter;
    this.loadTickets();
  }

  viewTicket(ticket: TicketSummaryDto): void {
    this.ticketService.getTicketWithResponses(ticket.ticketId).subscribe({
      next: (t) => (this.selectedTicket = t),
      error: (err) => console.error('Error loading ticket details:', err)
    });
  }

  closeTicketDetail(): void {
    this.selectedTicket = undefined;
    this.replyText = '';
  }

  sendReply(): void {
    if (!this.replyText.trim() || !this.selectedTicket) return;

    const dto: TicketResponseDto = {
      response: this.replyText,
      employeeId: this.profile?.employeeId ?? null,
      organizationId: null, // employee reply, so no orgId
      ticketId: this.selectedTicket.ticketId
    };

    this.ticketService.giveReplyToTicketResponse(dto).subscribe({
      next: (res) => {
        this.selectedTicket!.responses.push(res);
        this.replyText = '';
        this.loadTickets();
      },
      error: (err) => console.error('Error sending reply:', err)
    });
  }
}
