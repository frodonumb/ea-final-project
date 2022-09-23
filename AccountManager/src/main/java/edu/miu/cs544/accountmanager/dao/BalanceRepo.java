package edu.miu.cs544.accountmanager.dao;

import edu.miu.cs544.accountmanager.domain.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface BalanceRepo extends JpaRepository<Balance, UUID> {

    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    Optional<Balance> findByClientId(UUID clientID);
}
