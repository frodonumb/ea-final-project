package edu.miu.cs544.clientmanager.service;


import edu.miu.cs544.clientmanager.dto.TransactionDto;
import edu.miu.cs544.clientmanager.entity.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    Transaction makeTransaction(TransactionDto transactionDto);

    List<Transaction> getTransactionsByClient(UUID clientId);
}
