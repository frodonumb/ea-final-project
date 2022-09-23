package edu.miu.cs544.accountmanager.api;

import edu.miu.cs544.accountmanager.domain.Transaction;
import edu.miu.cs544.accountmanager.dto.TransactionDto;
import edu.miu.cs544.accountmanager.exceptions.NotEnoughBalanceException;

public interface Transactions {

    Transaction makeTransaction(TransactionDto dto) throws NotEnoughBalanceException;
}
