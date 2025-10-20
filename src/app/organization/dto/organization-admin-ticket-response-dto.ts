export interface OrganizationAdminTicketResponseDto {
  ticketId: number;
  organizationId?: number; // optional, backend extracts from auth
  response: string;
}
