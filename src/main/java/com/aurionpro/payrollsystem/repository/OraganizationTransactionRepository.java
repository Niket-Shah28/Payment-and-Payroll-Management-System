package com.aurionpro.payrollsystem.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aurionpro.payrollsystem.entity.transaction.Transaction;



public interface OraganizationTransactionRepository extends JpaRepository<Transaction, String> {

  

}

    
