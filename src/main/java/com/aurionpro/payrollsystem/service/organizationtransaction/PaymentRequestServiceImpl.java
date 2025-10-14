package com.aurionpro.payrollsystem.service.organizationtransaction;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.aurionpro.payrollsystem.dto.organizationtransaction.PaymentReqestDto;
import com.aurionpro.payrollsystem.entity.employee.Status;
import com.aurionpro.payrollsystem.entity.transaction.PaymentRequest;
import com.aurionpro.payrollsystem.repository.PaymentRequestRepository;

@Service
public class PaymentRequestServiceImpl implements PaymentRequestService {

	@Autowired
	private PaymentRequestRepository paymentRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Page<PaymentReqestDto> getAllPaymentRequests(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("scheduledTime").descending());
		Page<PaymentRequest> payments = paymentRepository.findAll(pageable);

		return payments.map(payment -> {
			PaymentReqestDto dto = modelMapper.map(payment, PaymentReqestDto.class);

			// Handle dynamic recipient name
			if (payment.getPaymentRecipientType() != null) {
				switch (payment.getPaymentRecipientType()) {
				case EMPLOYEE -> {
					if (payment.getEmployeeId() != null) {
						dto.setRecipientName(
								payment.getEmployeeId().getFirstName() + " " + payment.getEmployeeId().getLastName());
					}
				}
				case VENDOR -> {
					if (payment.getVendorId() != null) {
						dto.setRecipientName(payment.getVendorId().getName());
					}
				}
				}
			}

			if (payment.getOrganizationId() != null)
				dto.setOrganizationName(payment.getOrganizationId().getOrganizationName());

			dto.setStatus(payment.getStatus() != null ? payment.getStatus().name() : null);

			return dto;
		});
	}

	@Override
	public void updatePaymentStatus(Long paymentRequestId, Status status) {

		PaymentRequest request = paymentRepository.findById(paymentRequestId)
				.orElseThrow(() -> new RuntimeException("Payment request not found"));

		request.setStatus(status);
		paymentRepository.save(request);
	}

}
