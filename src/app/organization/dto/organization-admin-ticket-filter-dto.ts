import { TicketStatus } from "./ticket-status"; 

export interface OrganizationAdminTicketFilterDto {
  employeeId?: number;
  ticketId?: number;
  status?: TicketStatus;
  createdAfter?: string; // ISO string
  createdBefore?: string; // ISO string
}
