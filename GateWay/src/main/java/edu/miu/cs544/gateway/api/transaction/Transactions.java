package edu.miu.cs544.gateway.api.transaction;

import edu.miu.cs544.gateway.dto.transaction.TransactionDto;

import java.util.List;
import java.util.UUID;

public interface Transactions {

    TransactionDto makeTransaction(TransactionDto transactionDto);

    List<TransactionDto> getTransactionByClient(UUID id);

    List<TransactionDto> getMyTransactions();
}
