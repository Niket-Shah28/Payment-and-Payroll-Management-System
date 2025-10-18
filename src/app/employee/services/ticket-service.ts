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

  private baseUrl = 'http://localhost:8080/tickets'; 

  constructor(private http: HttpClient, private loginService: LoginService) {}

  private getHeaders(): HttpHeaders {
    const token = this.loginService.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  // Employee side
  createTicket(ticketCreateDto: TicketCreateDto): Observable<TicketDto> {
    return this.http.post<TicketDto>(
      `${this.baseUrl}/raise`,
      ticketCreateDto,
      { headers: this.getHeaders() }
    );
  }

 
  getAllTickets(): Observable<TicketSummaryDto[]> {
    return this.http.get<TicketSummaryDto[]>(
      `${this.baseUrl}/all`,
      { headers: this.getHeaders() }
    );
  }

  
  getOpenTickets(): Observable<TicketSummaryDto[]> {
    return this.http.get<TicketSummaryDto[]>(
      `${this.baseUrl}/open`,
      { headers: this.getHeaders() }
    );
  }


  getClosedTickets(): Observable<TicketSummaryDto[]> {
    return this.http.get<TicketSummaryDto[]>(
      `${this.baseUrl}/closed`,
      { headers: this.getHeaders() }
    );
  }


  getTicketWithResponses(ticketId: number): Observable<TicketDto> {
    return this.http.get<TicketDto>(
      `${this.baseUrl}/response/${ticketId}`,
      { headers: this.getHeaders() }
    );
  }

  // Reply to a ticket (POST /tickets/emp/respond)
  giveReplyToTicketResponse(responseDto: TicketResponseDto): Observable<TicketResponseDto> {
    return this.http.post<TicketResponseDto>(
      `${this.baseUrl}/emp/respond`,
      responseDto,
      { headers: this.getHeaders() }
    );
  }

  // Organization admin endpoints 
  
  closeTicket(closeDto: any): Observable<TicketDto> {
    return this.http.patch<TicketDto>(
      `${this.baseUrl}/close`,
      closeDto,
      { headers: this.getHeaders() }
    );
  }

  // Respond to ticket as organization admin (POST /tickets/org/respond)
  respondToTicketAsOrg(responseDto: any): Observable<TicketDto> {
    return this.http.post<TicketDto>(
      `${this.baseUrl}/org/respond`,
      responseDto,
      { headers: this.getHeaders() }
    );
  }

  // Filter tickets as organization admin (GET /tickets/filter)
  getTicketsWithFilters(filter: any): Observable<TicketDto[]> {
    return this.http.get<TicketDto[]>(
      `${this.baseUrl}/filter`,
      { params: filter, headers: this.getHeaders() }
    );
  }
}
