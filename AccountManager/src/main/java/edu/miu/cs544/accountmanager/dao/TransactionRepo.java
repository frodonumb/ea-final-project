package edu.miu.cs544.accountmanager.dao;

import edu.miu.cs544.accountmanager.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface TransactionRepo extends JpaRepository<Transaction, UUID> {

}
