package edu.miu.cs544.clientmanager.mapper;

import edu.miu.cs544.clientmanager.dto.TransactionDto;
import edu.miu.cs544.clientmanager.entity.Transaction;

public interface TransactionMapper {

    TransactionDto toDto(Transaction transaction);
}
