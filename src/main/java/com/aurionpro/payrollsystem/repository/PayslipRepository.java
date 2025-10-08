package com.aurionpro.payrollsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurionpro.payrollsystem.entity.employee.Payslip;

public interface PayslipRepository extends JpaRepository<Payslip, Long> {

}
