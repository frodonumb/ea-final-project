package edu.miu.cs544.accountmanager.mapper;

import edu.miu.cs544.accountmanager.domain.Transaction;
import edu.miu.cs544.accountmanager.dto.TransactionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    Transaction fromDto(TransactionDto dto);

    TransactionDto toDto(Transaction transaction);
}
