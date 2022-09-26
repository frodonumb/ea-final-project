package edu.miu.cs544.accountmanager.dto;

import edu.miu.cs544.accountmanager.domain.TransactionType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TransactionDto implements Serializable {

    private Long trxID;
    private TransactionType transactionType;
    private UUID clientId;
    private BigDecimal amount;
    private String description;
}
