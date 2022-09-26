package edu.miu.cs544.accountmanager.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Version;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Audited
@Table(name = "ac_transactions")
public class Transaction extends AbstractEntity {

    @Type(type = "pg-uuid")
    private UUID clientId;

    private BigDecimal amount;

    private Long trxID;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(nullable = false)
    private Date transactionDate;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Version
    private int version;

    public Transaction() {
        transactionDate = new Date();
    }
}
