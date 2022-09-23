package edu.miu.cs544.clientmanager.dto;


import edu.miu.cs544.clientmanager.entity.enums.TransactionType;
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
    private TransactionType type;
    private UUID clientId;
    private BigDecimal amount;
}
