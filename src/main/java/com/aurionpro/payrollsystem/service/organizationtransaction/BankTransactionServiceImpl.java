package com.aurionpro.payrollsystem.service.organizationtransaction;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.aurionpro.payrollsystem.dto.organizationtransaction.TransactionDetailsDto;
import com.aurionpro.payrollsystem.entity.transaction.Transaction;
import com.aurionpro.payrollsystem.repository.OraganizationTransactionRepository;
@Service
public class BankTransactionServiceImpl implements BankTransactionService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Page<TransactionDetailsDto> getFilteredTransactions(String entityName,
                                                               LocalDateTime dateFrom,
                                                               LocalDateTime dateTo,
                                                               int pageNumber,
                                                               int pageSize) {

        int offset = pageNumber * pageSize;

        String sql = "CALL get_transactions_with_entity(?, ?, ?, ?, ?)";

        @SuppressWarnings("deprecation")
		List<TransactionDetailsDto> transactions = jdbcTemplate.query(
                sql,
                new Object[]{entityName, dateFrom, dateTo, offset, pageSize},
                (rs, rowNum) -> {
                    TransactionDetailsDto dto = new TransactionDetailsDto();
                    dto.setTransactionId(rs.getString("transaction_id"));
                    dto.setAmount(rs.getDouble("amount"));
                    Timestamp ts = rs.getTimestamp("created_at");
                    dto.setCreatedAt(ts);
                    dto.setDescription(rs.getString("description"));
                    dto.setDestinationAccountNumber(rs.getString("destination_account_number"));
                    dto.setPaymentMode(rs.getString("payment_mode"));
                    dto.setPaymentType(rs.getString("payment_type"));
                    dto.setReceiverBankName(rs.getString("receiver_bank_name"));
                    dto.setReceiverHolderName(rs.getString("receiver_holder_name"));
                    dto.setReceiverIfscCode(rs.getString("receiver_ifsc_code"));
                    dto.setReference_number(rs.getString("reference_number")); // corrected naming
                    dto.setSourceAccountNumber(rs.getString("source_account_number"));
                    dto.setTransactionStatus(rs.getString("transaction_status"));
                   
                    return dto;
                }
        );

        // Wrap results into Page object
        return new PageImpl<>(transactions, PageRequest.of(pageNumber, pageSize), transactions.size());
    }

}