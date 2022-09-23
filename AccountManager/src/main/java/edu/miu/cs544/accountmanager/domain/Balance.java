package edu.miu.cs544.accountmanager.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@Audited
@Table(name = "ac_balances")
public class Balance extends AbstractEntity {

    @Column(unique = true)
    @Type(type = "pg-uuid")
    private UUID clientId;

    private BigDecimal balance;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Version
    private int version;
}
