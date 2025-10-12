package com.aurionpro.payrollsystem.service.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.aurionpro.payrollsystem.dto.transaction.PayslipDataDto;
import com.aurionpro.payrollsystem.entity.employee.Employee;
import com.aurionpro.payrollsystem.entity.employee.Payslip;
import com.aurionpro.payrollsystem.repository.PayslipRepository;

import jakarta.persistence.EntityManager;

public class PayslipService {
	
	@Autowired
	private PayslipRepository payslipRepository;
	
	@Autowired
	private EntityManager entityManager;

	@Async
	public void generateAndSavePayslip(PayslipDataDto payslipData){

      Payslip payslip = new Payslip();
      Employee employeeRef = entityManager.find(Employee.class, payslipData.getEmployeeId());
      payslip.setEmployee(employeeRef);
      payslip.setMonth(payslipData.getMonth());
      payslip.setYear(payslipData.getYear());
      payslip.setName(payslipData.getName());
      payslip.setDepartment(payslipData.getDepartment());
      payslip.setRole(payslipData.getRole());
      payslip.setBusinessUnit(payslipData.getBusinessUnit());
      payslip.setAccountNumber(payslipData.getAccountNumber());
      payslip.setIfscCode(payslipData.getIfscCode());
      payslip.setBankName(payslipData.getBankName());
      payslip.setBasicSalary(payslipData.getBasicSalary());
      payslip.setHouseRentAllowance(payslipData.getHouseRentAllowance());
      payslip.setDearnessAllowance(payslipData.getDearnessAllowance());
      payslip.setProvidentFund(payslipData.getProvidentFund());
      payslip.setOtherAllowance(payslipData.getOtherAllowance());
      payslip.setActualSalary(payslipData.getActualSalary());
      payslip.setFinalSalary(payslipData.getFinalSalary());
      payslipRepository.save(payslip);
      return;
	}
}
	
