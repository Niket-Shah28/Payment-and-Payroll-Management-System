package com.aurionpro.payrollsystem.dto.organizationtransaction;

import com.aurionpro.payrollsystem.entity.employee.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePaymentStatusDto {

	
    @NotBlank(message = "Status is required")
    private Status status;  
}

