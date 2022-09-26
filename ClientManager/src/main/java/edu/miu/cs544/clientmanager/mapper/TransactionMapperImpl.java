package edu.miu.cs544.clientmanager.mapper;

import edu.miu.cs544.clientmanager.dto.TransactionDto;
import edu.miu.cs544.clientmanager.entity.Transaction;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TransactionMapperImpl implements TransactionMapper {

    private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    @Override
    public TransactionDto toDto(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        TransactionDto transactionDto = new TransactionDto();

        transactionDto.setTrxID(transaction.getId());
        transactionDto.setTransactionType(transaction.getType());
        transactionDto.setClientId(transaction.getClient().getId());
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setDescription(transaction.getDescription());
        transactionDto.setStatus(transaction.getStatus());

        if (transaction.getCreatedAt() != null) {
            transactionDto.setCreatedAt(DATE_TIME_FORMAT.format(transaction.getCreatedAt()));
        }
        if (transaction.getUpdatedAt() != null) {
            transactionDto.setUpdatedAt(DATE_TIME_FORMAT.format(transaction.getUpdatedAt()));
        }

        return transactionDto;
    }
}
