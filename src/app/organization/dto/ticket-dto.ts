import { TicketStatus } from "./ticket-status"; 

export interface TicketResponseDto {
  responseId?: number;
  response: string;
  createdAt?: string;
  employeeId?: number;
  organizationId?: number;
  ticketId?: number;
}

export interface TicketDto {
  ticketId: number;
  query: string;
  createdAt: string;
  updatedAt?: string;
  status: TicketStatus;
  employeeId?: number;
  organizationId?: number;
  responses?: TicketResponseDto[];
}
