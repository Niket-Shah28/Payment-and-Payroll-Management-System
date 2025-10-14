package com.aurionpro.payrollsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aurionpro.payrollsystem.entity.bankAccount.BankAccountDetails;

@Repository
public interface BankAccountDetailsRepository extends JpaRepository<BankAccountDetails, Long> {
    
    Optional<BankAccountDetails> findByAccountNumber(String accountNumber);
    
    List<BankAccountDetails> findByIsActive(Boolean isActive);
    
    Optional<BankAccountDetails> findByAccountIdAndIsActive(Long accountId, Boolean isActive);
    
    boolean existsByAccountNumber(String accountNumber);
}
