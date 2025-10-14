package com.aurionpro.payrollsystem.service.employeeInterface;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurionpro.payrollsystem.dto.employeeAttendanceLeave.AttendanceMarkRequest;
import com.aurionpro.payrollsystem.dto.employeeAttendanceLeave.AttendanceResposeDto;
import com.aurionpro.payrollsystem.entity.attendance.Attendance;
import com.aurionpro.payrollsystem.entity.attendance.AttendanceId;
import com.aurionpro.payrollsystem.entity.employee.Employee;
import com.aurionpro.payrollsystem.entity.employee.EmployeeSalary;
import com.aurionpro.payrollsystem.entity.employee.Payslip;
import com.aurionpro.payrollsystem.entity.leave.LeaveStatus;
import com.aurionpro.payrollsystem.repository.AttendanceRepository;
import com.aurionpro.payrollsystem.repository.EmployeeRepository;
import com.aurionpro.payrollsystem.repository.EmployeeSalaryRepository;
import com.aurionpro.payrollsystem.repository.LeaveRequestRepository;
import com.aurionpro.payrollsystem.repository.PayslipRepository;

import jakarta.transaction.Transactional;

@Service
public class AttendanceServiceImpl implements AttendanceService{
	
	@Autowired
	private AttendanceRepository attendanceRepository;
	
	@Autowired
    private EmployeeRepository employeeRepository;
	
	@Autowired
    private LeaveRequestRepository leaveRequestRepository;
	
	@Autowired
	private PayslipRepository payslipRepository;
	
	@Autowired
	private EmployeeSalaryRepository employeeSalaryRepository;

    @Override
    @Transactional
    public List<AttendanceResposeDto> markAttendance(Long employeeId, AttendanceMarkRequest request) {
//        Long employeeId = request.getEmployeeId();
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + employeeId));

        List<AttendanceResposeDto> saved = new ArrayList<>();
        for (LocalDate date : request.getDates()) {
           
            boolean hasApprovedLeave = leaveRequestRepository.existsByEmployeeAndDateWithStatus(employeeId, date, LeaveStatus.APPROVED);
            if (hasApprovedLeave) {
                throw new IllegalStateException("Cannot mark attendance for " + date + " because leave already approved.");
            }

      
            AttendanceId id = new AttendanceId(date, employeeId);
            Attendance attendance = attendanceRepository.findById(id)
                    .orElse(new Attendance(id, employee, request.getAttendanceStatus()));
            attendance.setAttendanceStatus(request.getAttendanceStatus());
            attendance.setEmployee(employee);
            attendanceRepository.save(attendance);

            saved.add(new AttendanceResposeDto(date, attendance.getAttendanceStatus(), employeeId));
        }
        return saved;
    }

    @Override
    public List<AttendanceResposeDto> getAttendanceForEmployee(Long employeeId, LocalDate from, LocalDate to) {
        List<Attendance> list = attendanceRepository.findByAttendanceIdEmployeeIdAndAttendanceIdDateBetween(employeeId, from, to);
        List<AttendanceResposeDto> dto = new ArrayList<>();
        for (Attendance a : list) {
            dto.add(new AttendanceResposeDto(a.getAttendanceId().getDate(), a.getAttendanceStatus(), employeeId));
        }
        return dto;
    }
    

}
