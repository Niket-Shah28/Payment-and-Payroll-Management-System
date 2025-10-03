package com.aurionpro.payrollsystem.dto.projection;

public interface OrganizationRequestProjection {
	Long getRequestId();
    String getOrganizationName();
    String getEmail();
    String getPhoneNumber();
    String getAddress();
    String getCinNumber();
    String getNicCode();
    String getGstin();
    String getPan();
    String getTan();
}
