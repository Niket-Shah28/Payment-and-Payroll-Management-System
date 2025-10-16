import { TicketResponseDto } from './ticket-response-dto';
import { TicketStatus } from './ticket-status';

export interface TicketDto {
  ticketId: number;
  query: string;
  createdAt: string;
  updatedAt: string;
  status: TicketStatus;
  employeeId: number;
  organizationId: number;
  responses: TicketResponseDto[];
}
