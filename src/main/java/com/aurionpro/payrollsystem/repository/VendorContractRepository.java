package com.aurionpro.payrollsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurionpro.payrollsystem.entity.vendor.VendorContract;

public interface VendorContractRepository extends JpaRepository<VendorContract, Long>{

}
