export interface TicketResponseDto {
  responseId?: number;
  response: string;
  createdAt?: string;
  employeeId: number;
  organizationId: number;
  ticketId: number;
}
