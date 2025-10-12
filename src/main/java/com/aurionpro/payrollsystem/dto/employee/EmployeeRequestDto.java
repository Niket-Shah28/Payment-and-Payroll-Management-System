package com.aurionpro.payrollsystem.dto.employee;

import java.sql.Date;

import com.aurionpro.payrollsystem.entity.employee.Gender;
import com.aurionpro.payrollsystem.entity.employee.Salutation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class EmployeeRequestDto {
	@NotBlank(message = "First name is required")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Salutation is required")
    private Salutation salutation;

    private String spouse;

    @Past(message = "Date of birth must be in the past")
    @NotNull(message = "Date of birth is required")
    private Date dateOfBirth;

    @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Invalid blood group format")
    private String bloodGroup;

    @NotBlank(message = "Nationality is required")
    private String nationality;

    @Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$", message = "Invalid PAN number format")
    @NotBlank(message = "PAN number is required")
    private String panNumber;

    @Pattern(regexp = "^[0-9]{12}$", message = "Aadhar number must be 12 digits")
    @NotBlank(message = "Aadhar number is required")
    private String aadharNumber;

    private Long managerId;

    @Email(message = "Invalid office email")
    @NotBlank(message = "Office email is required")
    private String officeEmail;

    @Email(message = "Invalid personal email")
    @NotBlank(message = "Personal email is required")
    private String personalEmail;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @Positive(message = "Basic salary must be positive")
    @NotNull(message = "Basic salary is required")
    private Double basicSalary;

    @PositiveOrZero(message = "House rent allowance must be non-negative")
    private Double houseRentAllowance;

    @PositiveOrZero(message = "Dearness allowance must be non-negative")
    private Double dearnessAllowance;

    @PositiveOrZero(message = "Provident fund must be non-negative")
    private Double providentFund;

    @PositiveOrZero(message = "Other allowance must be non-negative")
    private Double otherAllowance;

    @Positive(message = "Final salary must be positive")
    @NotNull(message = "Final salary is required")
    private Double finalSalary;

    @NotNull(message = "Grade is required")
    @Min(value = 1, message = "Grade must be at least 1")
    private Integer grade;

    @NotNull(message = "Employee role ID is required")
    private Long employeeRoleId;

    @NotNull(message = "Business unit ID is required")
    private Long businessUnitId;

    @NotNull(message = "Department ID is required")
    private Long departmentId;
}
