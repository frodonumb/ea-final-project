package edu.miu.cs544.clientmanager.service;

import edu.miu.cs544.dto.TransactionDto;
import edu.miu.cs544.entity.Transaction;

import java.util.UUID;

public interface TransactionService {
    void makeTransaction(TransactionDto transactionDto);
}
