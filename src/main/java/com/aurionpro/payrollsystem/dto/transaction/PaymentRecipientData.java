package com.aurionpro.payrollsystem.dto.transaction;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class PaymentRecipientData implements Serializable{
	private Long employeeId;
	private String name;
	private String email;
	private String department;
	private String role;
	private String businessUnit;
	private String accountNumber;
	private String ifscCode;
	private String bankName;
	private String accountHolderName;
	private Double basicSalary;
	private Double houseRentAllowance;
	private Double dearnessAllowance;
	private Double providentFund;
	private Double otherAllowance;
	private Double actualSalary;
	private Double finalSalary;
	private Integer attendance;
}
