package com.aurionpro.payrollsystem.dto.organization;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class EmployeePageResponseDto {
    private List<EmployeeDto> content;
    private boolean hasNextPage;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;
}
