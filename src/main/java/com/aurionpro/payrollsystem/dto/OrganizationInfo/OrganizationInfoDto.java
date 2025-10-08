package com.aurionpro.payrollsystem.dto.OrganizationInfo;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class OrganizationInfoDto {
    private Long organizationId;
    private String organizationName;
    private String email;
    private String phoneNumber;
    private String address;
    private String gstin;
    private String pan;
    private String tan;
    private Integer nicCode;
    private Timestamp createdAt;
 
}

