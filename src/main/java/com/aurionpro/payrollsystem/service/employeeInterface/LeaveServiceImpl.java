package com.aurionpro.payrollsystem.service.employeeInterface;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurionpro.payrollsystem.dto.employeeAttendanceLeave.LeaveApplyRequestDto;
import com.aurionpro.payrollsystem.dto.employeeAttendanceLeave.LeaveDecisionDto;
import com.aurionpro.payrollsystem.dto.employeeAttendanceLeave.LeaveResponseDto;
import com.aurionpro.payrollsystem.entity.attendance.Attendance;
import com.aurionpro.payrollsystem.entity.attendance.AttendanceId;
import com.aurionpro.payrollsystem.entity.attendance.AttendanceStatus;
import com.aurionpro.payrollsystem.entity.employee.Employee;
import com.aurionpro.payrollsystem.entity.leave.EmployeeLeaves;
import com.aurionpro.payrollsystem.entity.leave.LeaveRequest;
import com.aurionpro.payrollsystem.entity.leave.LeaveStatus;
import com.aurionpro.payrollsystem.entity.leave.LeaveType;
import com.aurionpro.payrollsystem.repository.AttendanceRepository;
import com.aurionpro.payrollsystem.repository.EmployeeLeavesRepository;
import com.aurionpro.payrollsystem.repository.EmployeeRepository;
import com.aurionpro.payrollsystem.repository.LeaveRequestRepository;
import com.aurionpro.payrollsystem.repository.LeaveTypeRepository;

import jakarta.transaction.Transactional;

@Service
public class LeaveServiceImpl implements LeaveService{
	
	@Autowired
	private LeaveRequestRepository leaveRequestRepository;
	
	@Autowired
    private LeaveTypeRepository leaveTypeRepository;
	
	@Autowired
    private EmployeeRepository employeeRepository;
	
	@Autowired
    private EmployeeLeavesRepository employeeLeavesRepository;
	
	@Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    @Transactional
    public LeaveResponseDto applyLeave(Long employeeId, Long managerId, LeaveApplyRequestDto dto) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + employeeId));

        LeaveType leaveType = leaveTypeRepository.findById(dto.getLeaveTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Leave type not found: " + dto.getLeaveTypeId()));

        LeaveRequest lr = new LeaveRequest();
        lr.setEmployeeId(employee);
        lr.setLeaveTypeId(leaveType);
        lr.setReason(dto.getReason());
        lr.setStartDate(dto.getStartDate());
        lr.setEndDate(dto.getEndDate());
        lr.setLeaveStatus(LeaveStatus.PENDING); 
        leaveRequestRepository.save(lr);

        EmployeeLeaves rem = employeeLeavesRepository.findByEmployeeAndLeaveType(employee, leaveType).orElse(null);
        LeaveResponseDto resp = new LeaveResponseDto(
                lr.getLeaveRequestId(),
                employeeId,
                managerId, 
                leaveType.getLeaveTypeId(),
                lr.getStartDate(), lr.getEndDate(), LeaveStatus.PENDING, lr.getReason(), rem
        );
        return resp;
    }

    @Override
    public List<LeaveResponseDto> getLeavesForEmployee(Long employeeId) {
        List<LeaveRequest> list = leaveRequestRepository.findByEmployeeIdEmployeeId(employeeId);
        List<LeaveResponseDto> result = new ArrayList<>();
        for (LeaveRequest lr : list) {
            EmployeeLeaves rem = employeeLeavesRepository.findByEmployeeAndLeaveType(lr.getEmployeeId(), lr.getLeaveTypeId()).orElse(null);
            Long mgrId = lr.getManagerId() != null ? lr.getManagerId().getEmployeeId() : null;
            
            LeaveResponseDto dto = new LeaveResponseDto();
            dto.setLeaveRequestId(lr.getLeaveRequestId());
            dto.setEmployeeId(lr.getEmployeeId().getEmployeeId());
//            dto.setManagerId(lr.getManagerId() != null ? lr.getManagerId().getEmployeeId() : null);
//            dto.setLeaveTypeId(lr.getLeaveTypeId().getLeaveTypeId());
            dto.setManagerId(lr.getManagerId().getEmployeeId());
            dto.setLeaveTypeId(lr.getLeaveTypeId().getLeaveTypeId());
            dto.setStartDate(lr.getStartDate());
            dto.setEndDate(lr.getEndDate());
            dto.setLeaveStatus(lr.getLeaveStatus());
            dto.setReason(lr.getReason());
            dto.setRemainingDays(rem);
            result.add(dto);
        }
        return result;
    }

    @Override
    public List<LeaveResponseDto> getLeavesForManager(Long managerId) {
        List<LeaveRequest> list = leaveRequestRepository.findByManagerIdEmployeeId(managerId);
        List<LeaveResponseDto> result = new ArrayList<>();
        for (LeaveRequest lr : list) {
            EmployeeLeaves rem = employeeLeavesRepository.findByEmployeeAndLeaveType(lr.getEmployeeId(), lr.getLeaveTypeId()).orElse(null);
            result.add(new LeaveResponseDto(
                    lr.getLeaveRequestId(),
                    lr.getEmployeeId().getEmployeeId(),
                    lr.getManagerId() != null ? lr.getManagerId().getEmployeeId() : null,
                    lr.getLeaveTypeId().getLeaveTypeId(),
                    lr.getStartDate(), lr.getEndDate(), lr.getLeaveStatus(),
                    lr.getReason(),
                    rem
            ));
        }
        return result;
    }

    @Override
    @Transactional
    public LeaveResponseDto decideLeave(LeaveDecisionDto dto, Long managerId) {
		return null;
//        LeaveRequest lr = leaveRequestRepository.findById(dto.getLeaveRequestId())
//                .orElseThrow(() -> new IllegalArgumentException("Leave request not found: " + dto.getLeaveRequestId()));
//
//        // manager check could be added: verify the managerId is authorized to act on this request
//        Employee manager = employeeRepository.findById(managerId)
//                .orElseThrow(() -> new IllegalArgumentException("Manager not found: " + managerId));
//        lr.setManagerId(manager);
//
//        if (dto.getLeaveStatus() == LeaveStatus.Approved) {
//            // calculate days inclusive
//            long days = ChronoUnit.DAYS.between(lr.getStartDate(), lr.getEndDate()) + 1;
//            EmployeeLeaves empLeaves = employeeLeavesRepository.findByEmployeeAndLeaveType(lr.getEmployeeId(), lr.getLeaveTypeId())
//                    .orElseThrow(() -> new IllegalStateException("No leave balance configured for this employee and leave type"));
//
//            if (empLeaves.getRemainingCount() < days) {
//                throw new IllegalStateException("Employee does not have enough leave balance. Remaining: " + empLeaves.getRemainingCount());
//            }
//
//            // deduct
//            empLeaves.setRemainingCount(empLeaves.getRemainingCount() - (int) days);
//            employeeLeavesRepository.save(empLeaves);
//
//            // set leave status and create attendance LEAVE records for each date
//            lr.setLeaveStatus(LeaveStatus.Approved);
//            LocalDate date = lr.getStartDate();
//            while (!date.isAfter(lr.getEndDate())) {
//                AttendanceId aid = new AttendanceId(date, lr.getEmployeeId().getEmployeeId());
//                // upsert attendance as LEAVE
//                Attendance a = attendanceRepository.findById(aid).orElse(new Attendance(aid, lr.getEmployeeId(), AttendanceStatus.LEAVE));
//                a.setAttendanceStatus(AttendanceStatus.LEAVE);
//                attendanceRepository.save(a);
//                date = date.plusDays(1);
//            }
//
//        } else if (dto.getLeaveStatus() == LeaveStatus.Rejected) {
//            lr.setLeaveStatus(LeaveStatus.Rejected);
//        } else {
//            lr.setLeaveStatus(LeaveStatus.Pending);
//        }
//
//        leaveRequestRepository.save(lr);
//
//        EmployeeLeaves rem = employeeLeavesRepository.findByEmployeeAndLeaveType(lr.getEmployeeId(), lr.getLeaveTypeId()).orElse(null);
//        return new LeaveResponseDto(
//                lr.getLeaveRequestId(),
//                lr.getEmployeeId().getEmployeeId(),
//                lr.getManagerId() != null ? lr.getManagerId().getEmployeeId() : null,
//                lr.getLeaveTypeId().getLeaveTypeId(),
//                lr.getStartDate(), lr.getEndDate(), lr.getLeaveStatus(), lr.getReason(), rem
//        );
    }

}
