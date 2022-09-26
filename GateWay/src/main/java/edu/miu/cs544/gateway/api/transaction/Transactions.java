package edu.miu.cs544.gateway.api.transaction;

import edu.miu.cs544.gateway.dto.transaction.TransactionDto;

public interface Transactions {

    void makeTransaction(TransactionDto transactionDto);
}
