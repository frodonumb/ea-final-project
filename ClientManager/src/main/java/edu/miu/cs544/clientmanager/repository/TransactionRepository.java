package edu.miu.cs544.clientmanager.repository;

import edu.miu.cs544.clientmanager.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("select t from Transaction t where t.client.id = :clientId ORDER BY t.updatedAt desc ")
    List<Transaction> findAllByClientId(UUID clientId);
}
