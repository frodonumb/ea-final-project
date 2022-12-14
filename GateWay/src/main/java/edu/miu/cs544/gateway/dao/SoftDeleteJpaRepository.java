package edu.miu.cs544.gateway.dao;

import edu.miu.cs544.gateway.domain.AbstractEntity;
import edu.miu.cs544.gateway.exceptions.ErrorCode;
import edu.miu.cs544.gateway.exceptions.LocalizedApplicationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@NoRepositoryBean
public interface SoftDeleteJpaRepository<T extends AbstractEntity> extends JpaRepository<T, UUID>, JpaSpecificationExecutor<T> {

    @Override
    @Transactional
    @Modifying
    default void deleteById(UUID id) {
        findById(id).orElseThrow(() -> new LocalizedApplicationException(ErrorCode.ENTITY_NOT_FOUND)).setDeleted(true);
    }

    @Override
    @Transactional
    default void delete(T entity) {
        entity.setDeleted(true);
    }

    @Override
    @Transactional
    default void deleteAll(Iterable<? extends T> entities) {
        entities.forEach(this::delete);
    }

    @Transactional
    @Modifying
    default void softDeleteAll() {
        findAll().forEach(this::delete);
    }

}
