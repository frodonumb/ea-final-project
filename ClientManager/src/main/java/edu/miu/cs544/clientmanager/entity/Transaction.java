package edu.miu.cs544.clientmanager.entity;

import edu.miu.cs544.clientmanager.entity.enums.TransactionStatus;
import edu.miu.cs544.clientmanager.entity.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private TransactionType type;
    @Enumerated(value = EnumType.STRING)
    private TransactionStatus status;
    private BigDecimal amount;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
