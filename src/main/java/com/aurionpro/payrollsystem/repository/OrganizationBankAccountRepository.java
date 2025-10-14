package com.aurionpro.payrollsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurionpro.payrollsystem.entity.bankAccount.OrganizationBankAccount;

public interface OrganizationBankAccountRepository extends JpaRepository<OrganizationBankAccount, Long> {
	Optional<OrganizationBankAccount> findByOrganization_OrganizationIdAndIsActiveTrue(Long organizationId);
	Optional<OrganizationBankAccount> findByAccountIdAndIsActiveTrue(Long accountId);
	Optional<OrganizationBankAccount> findByOrganization_OrganizationId(Long organizationId);
}
