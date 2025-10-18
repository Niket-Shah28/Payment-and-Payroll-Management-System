package com.aurionpro.payrollsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aurionpro.payrollsystem.dto.vendor.VendorResponseDto;
import com.aurionpro.payrollsystem.entity.vendor.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long>{
	
	@Query("SELECT new com.aurionpro.payrollsystem.dto.vendor.VendorResponseDto( "
		 + "vendorId, name, email, phoneNumber, gstin, pan) "
		 + "FROM Vendor v "
		 + "WHERE v.organization.organizationId = :organizationId AND v.isActive = TRUE")
	List<VendorResponseDto> getVendors(@Param(value = "organizationId") Long organizationId);
}
