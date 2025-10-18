package com.aurionpro.payrollsystem.dto.vendor;

import java.time.LocalDate;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class VendorDto {
	@NotNull
	private String name;
	
	@Pattern(regexp = "^[LU][0-9]{5}[A-Z]{2}[0-9]{4}[A-Z]{3}[0-9]{6}$")
	private String cinNumber;
	
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message ="Email Format is not correct")
	private String email;
	
	@Pattern(regexp = "^[6-9]{1}[0-9]{9}$", message = "Phone Number Format is incorrect")
	private String phoneNumber;
	
	@NotNull
	private String address;
	
	@Pattern(regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$")
	private String gstin;
	
	@Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$")
	private String pan;
	
	@Pattern(regexp = "^[A-Z]{4}[0-9]{5}[A-Z]{1}$")
	private String tan;
	
	@NotNull
	private String contractTitle;
	
	@NotNull
	private LocalDate startDate;
	
	@NotNull
	private LocalDate endDate;
	
	@URL
	private String contractDocumentUrl;
}
