package edu.miu.cs544.accountmanager.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import java.util.UUID;

@Getter
@Setter
@Audited
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class AbstractEntity {

    @Id
    @Type(type = "pg-uuid")
    private UUID id;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    public AbstractEntity() {
        this.id = UUID.randomUUID();
    }
}
