package com.aurionpro.payrollsystem.dto.OrganizationInfo;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDocumentDto {
    
    private String cloudinaryUrl;
    private Timestamp createdAt;
    private String documentTypeName; 
}

