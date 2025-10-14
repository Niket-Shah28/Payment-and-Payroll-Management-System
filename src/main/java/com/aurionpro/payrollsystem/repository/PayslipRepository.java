package com.aurionpro.payrollsystem.repository;

import java.time.Month;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aurionpro.payrollsystem.entity.employee.Employee;
import com.aurionpro.payrollsystem.entity.employee.Payslip;

@Repository
public interface PayslipRepository extends JpaRepository<Payslip, Long> {
	
	 Optional<Payslip> findByEmployeeAndMonthAndYear(Employee employeeId, Month month, Integer year);
	 
	 //

	List<Payslip> findByEmployee_EmployeeIdOrderByYearDescMonthDesc(Long employeeId);

	Optional<Payslip> findByEmployee_EmployeeIdAndMonthAndYear(Long employeeId, Month month, Integer year);
	

	
	List<Payslip> findByEmployee_EmployeeIdAndYearOrderByMonthDesc(Long employeeId, Integer year);

	
	@Query("SELECT p FROM Payslip p " + "LEFT JOIN FETCH p.employee " + "WHERE p.employee.employeeId = :employeeId "
			+ "ORDER BY p.year DESC, p.month DESC")
	List<Payslip> findByEmployeeWithDetails(@Param("employeeId") Long employeeId);

	@Query("SELECT p FROM Payslip p " + "LEFT JOIN FETCH p.employee " + "WHERE p.payslipId = :payslipId")
	Optional<Payslip> findByIdWithDetails(@Param("payslipId") Long payslipId);

	
	@Query("SELECT p FROM Payslip p " + "WHERE p.employee.employeeId = :employeeId "
			+ "ORDER BY p.year DESC, p.month DESC " + "LIMIT 1")
	Optional<Payslip> findLatestPayslipByEmployeeId(@Param("employeeId") Long employeeId);

	
	Long countByEmployee_EmployeeId(Long employeeId);

	
	boolean existsByEmployee_EmployeeIdAndMonthAndYear(Long employeeId, Month month, Integer year);
}
