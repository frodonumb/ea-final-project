package edu.miu.cs544.clientmanager.dto;


import edu.miu.cs544.clientmanager.entity.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionDto implements Serializable {
    private TransactionType transactionType;
    private UUID clientId;
    private BigDecimal amount;
}
