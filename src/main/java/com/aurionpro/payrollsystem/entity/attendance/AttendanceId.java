package com.aurionpro.payrollsystem.entity.attendance;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;


@SuppressWarnings("serial")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class AttendanceId implements Serializable{
	private LocalDate date;
    private Long employeeId;
}
