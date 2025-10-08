package com.aurionpro.payrollsystem.service.payment;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.aurionpro.payrollsystem.dto.transaction.PaymentRecipientData;

@Component
public class PaymentProcessor implements ItemProcessor<PaymentRecipientData, PaymentRecipientData> {
    @Override
    public PaymentRecipientData process(PaymentRecipientData item) {
//        if (item.getFinalSalary() == null || item.getFinalSalary() <= 0) {
//            return null;
//        }
        return item;
    }
}
