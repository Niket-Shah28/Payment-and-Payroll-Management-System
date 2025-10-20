import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TicketDto, TicketResponseDto } from '../dto/ticket-dto';
import { TicketSummaryDto } from '../dto/ticket-summary-dto';
import { OrganizationAdminTicketFilterDto } from '../dto/organization-admin-ticket-filter-dto';
import { OrganizationAdminTicketResponseDto } from '../dto/organization-admin-ticket-response-dto';
import { OrganizationAdminTicketCloseDto } from '../dto/organization-admin-ticket-close-dto';

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  private baseUrl = 'http://localhost:8080/tickets';

  constructor(private http: HttpClient) {}

  // 1. Get tickets with filters
  getTicketsWithFilters(filterDto: OrganizationAdminTicketFilterDto): Observable<TicketDto[]> {
    let params = new HttpParams();
    Object.keys(filterDto).forEach(key => {
      if (filterDto[key as keyof OrganizationAdminTicketFilterDto]) {
        params = params.set(key, filterDto[key as keyof OrganizationAdminTicketFilterDto]!.toString());
      }
    });
    return this.http.get<TicketDto[]>(`${this.baseUrl}/filter`, { params });
  }

  // 2. Reply to a ticket
  respondToTicket(responseDto: OrganizationAdminTicketResponseDto): Observable<TicketDto> {
    return this.http.post<TicketDto>(`${this.baseUrl}/org/respond`, responseDto);
  }

  // 3. Close a ticket
  closeTicket(closeDto: OrganizationAdminTicketCloseDto): Observable<TicketDto> {
    return this.http.patch<TicketDto>(`${this.baseUrl}/close`, closeDto);
  }

  
}
