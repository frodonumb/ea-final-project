package edu.miu.cs544.gateway.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto implements Serializable {

    private Long trxID;
    private TransactionType transactionType;
    private UUID clientId;
    private BigDecimal amount;
    private String description;
    private String status;
    private String createdAt;
    private String updatedAt;
}
