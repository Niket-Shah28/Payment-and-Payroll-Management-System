package com.aurionpro.payrollsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aurionpro.payrollsystem.entity.bankAccount.EmployeeBankInfo;
import com.aurionpro.payrollsystem.entity.employee.Employee;

@Repository
public interface EmployeeBankInfoRepository extends JpaRepository<EmployeeBankInfo, Long> {
    
	 Optional<EmployeeBankInfo> findByEmployee(Employee employeeId);
	
	//
    Optional<EmployeeBankInfo> findByEmployee_EmployeeId(Long employeeId);
    
    List<EmployeeBankInfo> findByIsActive(Boolean isActive);
    
    Optional<EmployeeBankInfo> findByEmployeeBankInfoIdAndIsActive(Long employeeBankInfoId, Boolean isActive);
    
    @Query("SELECT ebi FROM EmployeeBankInfo ebi WHERE ebi.employee.employeeId = :employeeId AND ebi.isActive = true")
    Optional<EmployeeBankInfo> findActiveByEmployeeId(@Param("employeeId") Long employeeId);
    
    boolean existsByPfNumber(String pfNumber);
    
    boolean existsByUanNumber(String uanNumber);
}
