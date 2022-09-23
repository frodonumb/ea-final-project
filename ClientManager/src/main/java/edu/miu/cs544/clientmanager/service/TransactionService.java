package edu.miu.cs544.clientmanager.service;


import edu.miu.cs544.clientmanager.dto.TransactionDto;

public interface TransactionService {
    void makeTransaction(TransactionDto transactionDto);
}
