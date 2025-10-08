package com.aurionpro.payrollsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aurionpro.payrollsystem.entity.organization.Organization;

@Repository
public interface OrganizationInfoRepository extends JpaRepository<Organization, Long> {

    @Query("SELECT o FROM Organization o ORDER BY o.createdAt DESC")
    Page<Organization> findAllOrganizations(Pageable pageable);
}

