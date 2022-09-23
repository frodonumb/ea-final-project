package edu.miu.cs544.clientmanager.repository;

import edu.miu.cs544.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
