import { TicketStatus } from "./ticket-status"; 

export interface TicketSummaryDto {
  ticketId: number;
  query: string;
  createdAt: string;
  updatedAt?: string;
  status: TicketStatus;
  employeeId?: number;
  employeeName?: string;
  organizationId?: number;
  organizationName?: string;
  responseCount?: number;
}
