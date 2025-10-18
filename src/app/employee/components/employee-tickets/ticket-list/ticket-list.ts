import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TicketService } from '../../../services/ticket-service';
import { TicketSummaryDto } from '../../../dto/ticket-summary-dto'; 

@Component({
  selector: 'app-ticket-list',
  standalone: false,
  templateUrl: './ticket-list.html',
  styleUrls: ['./ticket-list.css'] 
})
export class TicketList {

  tickets: TicketSummaryDto[] = [];
  filter: 'ALL' | 'OPEN' | 'CLOSE' = 'ALL';

  constructor(
    private ticketService: TicketService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadTickets();
  }

  loadTickets() {
    if (this.filter === 'OPEN') {
      this.ticketService.getOpenTickets().subscribe(t => this.tickets = t);
    } else if (this.filter === 'CLOSE') {
      this.ticketService.getClosedTickets().subscribe(t => this.tickets = t);
    } else {
      this.ticketService.getAllTickets().subscribe(t => this.tickets = t);
    }
  }

  setFilter(f: 'ALL' | 'OPEN' | 'CLOSE') {
    this.filter = f;
    this.loadTickets();
  }

  
  viewTicket(ticketId: number): void {
  console.log('Navigating to ticket:', ticketId);
  this.router.navigate(['/employee/dashboard/tickets/', ticketId]);
}

//  editTicketQuery(ticket: TicketSummaryDto): void {
//     const newQuery = prompt('Enter new query:', ticket.query);
//     if (!newQuery || newQuery.trim() === '') return;

//     this.ticketService.updateTicket(ticket.ticketId, newQuery).subscribe({
//       next: updatedTicket => {
//         alert('Query updated successfully!');
//         ticket.query = updatedTicket.query; // update the UI immediately
//       },
//       error: (err:any) => console.error(err)
//     });
//   }

}