import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TicketCreateDto } from '../dto/ticket-create-dto'; 
import { TicketDto } from '../dto/ticket-dto'; 
import { TicketSummaryDto } from '../dto/ticket-summary-dto'; 
import { TicketResponseDto } from '../dto/ticket-response-dto';
import { LoginService } from '../../auth/service/login-service'; 

@Injectable({
  providedIn: 'root'
})
export class TicketService {

private baseUrl = 'http://localhost:8080/employee/tickets';

  constructor(private http: HttpClient, private loginService: LoginService) {}

  private getHeaders(): HttpHeaders {
    const token = this.loginService.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  // Create a new ticket
  createTicket(ticketCreateDto: TicketCreateDto): Observable<TicketDto> {
    return this.http.post<TicketDto>(
      this.baseUrl,
      ticketCreateDto,
      { headers: this.getHeaders() }
    );
  }

  // Get all tickets for logged-in employee
  getAllTickets(): Observable<TicketSummaryDto[]> {
    return this.http.get<TicketSummaryDto[]>(
      this.baseUrl,
      { headers: this.getHeaders() }
    );
  }

  // Get open tickets
  getOpenTickets(): Observable<TicketSummaryDto[]> {
    return this.http.get<TicketSummaryDto[]>(
      `${this.baseUrl}/open`,
      { headers: this.getHeaders() }
    );
  }

  // Get closed tickets
  getClosedTickets(): Observable<TicketSummaryDto[]> {
    return this.http.get<TicketSummaryDto[]>(
      `${this.baseUrl}/close`,
      { headers: this.getHeaders() }
    );
  }

  // Get ticket details with responses
  getTicketWithResponses(ticketId: number): Observable<TicketDto> {
    return this.http.get<TicketDto>(
      `${this.baseUrl}/${ticketId}`,
      { headers: this.getHeaders() }
    );
  }

  // Reply to a ticket response
  giveReplyToTicketResponse(responseDto: TicketResponseDto): Observable<TicketResponseDto> {
    return this.http.post<TicketResponseDto>(
      `${this.baseUrl}/response`,
      responseDto,
      { headers: this.getHeaders() }
    );
  }

  // Update ticket query
  updateTicket(ticketId: number, newQuery: string): Observable<TicketDto> {
    return this.http.patch<TicketDto>(
      `${this.baseUrl}/${ticketId}`,
      { query: newQuery },
      { headers: this.getHeaders() }
    );
  }
  
}
