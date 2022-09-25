package edu.miu.cs544.accountmanager.api;

import edu.miu.cs544.accountmanager.dao.BalanceRepo;
import edu.miu.cs544.accountmanager.dao.TransactionRepo;
import edu.miu.cs544.accountmanager.domain.Balance;
import edu.miu.cs544.accountmanager.domain.Transaction;
import edu.miu.cs544.accountmanager.domain.TransactionType;
import edu.miu.cs544.accountmanager.dto.TransactionDto;
import edu.miu.cs544.accountmanager.dto.UserDto;
import edu.miu.cs544.accountmanager.exceptions.NotEnoughBalanceException;
import edu.miu.cs544.accountmanager.mapper.TransactionMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
public class TransactionService implements Transactions {

    private final TransactionMapper transactionMapper;
    private final TransactionRepo transactionRepo;
    private final BalanceRepo balanceRepo;

    @Override
    public Transaction makeTransaction(TransactionDto dto) throws NotEnoughBalanceException {
        Transaction transaction = transactionMapper.fromDto(dto);
        transaction = transactionRepo.saveAndFlush(transaction);
        Balance balance = makeOperationOnBalance(transaction, getBalanceByClient(transaction.getClientId()));
        balanceRepo.saveAndFlush(balance);
        return transaction;
    }

    @RabbitListener(queues = {"CLIENT_TRANSACTION"})
    public void listenOnMakeTransaction(TransactionDto dto) {
        try {
            makeTransaction(dto);
        } catch (Exception ex) {
            throw new AmqpRejectAndDontRequeueException("Ops, an error! Message should go to DLX and DLQ");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    @RabbitListener(queues = {"CLIENT_CREATED"})
    public void onClientCreated(UserDto client) {
        log.info("mq was received with queue name CLIENT_CREATED");
        Balance balance = new Balance();
        balance.setBalance(BigDecimal.ZERO);
        balance.setClientId(client.getId());
        balanceRepo.saveAndFlush(balance);
    }

    private boolean hasClientEnoughBalance(Transaction transaction) {
        Balance balance = getBalanceByClient(transaction.getClientId());
        return balance.getBalance().subtract(transaction.getAmount()).compareTo(BigDecimal.ZERO) >= 0;
    }

    private Balance getBalanceByClient(UUID clientId) {
        return balanceRepo.findByClientId(clientId).orElseThrow(() -> new EntityNotFoundException("There is no balance for client with id: " + clientId));
    }

    private Balance subtractBalance(Transaction transaction, Balance balance) throws NotEnoughBalanceException {
        if (!hasClientEnoughBalance(transaction)) {
            throw new NotEnoughBalanceException();
        }
        balance.setBalance(balance.getBalance().subtract(transaction.getAmount()));
        return balance;
    }

    private Balance addBalance(Transaction transaction, Balance balance) {
        balance.setBalance(balance.getBalance().add(transaction.getAmount()));
        return balance;
    }

    private Balance makeOperationOnBalance(Transaction transaction, Balance balance) throws NotEnoughBalanceException {
        return transaction.getTransactionType() == TransactionType.WITHDRAW ? subtractBalance(transaction, balance) : addBalance(transaction, balance);
    }
}
