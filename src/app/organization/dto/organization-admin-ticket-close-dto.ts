export interface OrganizationAdminTicketCloseDto {
  ticketId: number;
  organizationId?: number; // optional, backend extracts from auth
}
