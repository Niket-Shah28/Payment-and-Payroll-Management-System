package com.aurionpro.payrollsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aurionpro.payrollsystem.entity.ticket.TicketResponse;

@Repository
public interface TicketResponseRepository extends JpaRepository<TicketResponse, Long> {

	List<TicketResponse> findByTicketId_TicketId(Long ticketId);

	List<TicketResponse> findByTicketId_TicketIdOrderByCreatedAtAsc(Long ticketId);

	List<TicketResponse> findByEmployeeId_EmployeeId(Long employeeId);

	List<TicketResponse> findByOrganizationId_OrganizationId(Long organizationId);

	Long countByTicketId_TicketId(Long ticketId);

	@Query("SELECT tr FROM TicketResponse tr " + "LEFT JOIN FETCH tr.employeeId " + "LEFT JOIN FETCH tr.organizationId "
			+ "LEFT JOIN FETCH tr.ticketId " + "WHERE tr.ticketId.ticketId = :ticketId " + "ORDER BY tr.createdAt ASC")
	List<TicketResponse> findByTicketIdWithDetails(@Param("ticketId") Long ticketId);
}
