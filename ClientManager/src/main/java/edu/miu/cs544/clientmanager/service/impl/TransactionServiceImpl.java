package edu.miu.cs544.clientmanager.service.impl;

import edu.miu.cs544.clientmanager.dto.TransactionDto;
import edu.miu.cs544.clientmanager.entity.Client;
import edu.miu.cs544.clientmanager.entity.Transaction;
import edu.miu.cs544.clientmanager.entity.enums.TransactionStatus;
import edu.miu.cs544.clientmanager.repository.ClientRepository;
import edu.miu.cs544.clientmanager.repository.TransactionRepository;
import edu.miu.cs544.clientmanager.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ClientRepository clientRepository;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void makeTransaction(TransactionDto transactionDto) {
        log.info(transactionDto.toString());
        Transaction transaction = createTransactionObject(transactionDto);
        transaction = transactionRepository.saveAndFlush(transaction);
        transactionDto.setTrxID(transaction.getId());
        rabbitTemplate.convertAndSend("CLIENT_TRANSACTION", transactionDto);
        transaction.setStatus(TransactionStatus.PROCESSING);
        transactionRepository.save(transaction);
    }

    @RabbitListener(queues = {"CLIENT_TRANSACTION_SUCCESS"})
    public void listenSuccess(TransactionDto transactionDto) {
        try {
            Transaction transaction = getById(transactionDto.getTrxID());
            transaction.setStatus(TransactionStatus.COMPLETED);
            transactionRepository.save(transaction);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    @RabbitListener(queues = {"CLIENT_TRANSACTION_FAILED"})
    public void listenError(TransactionDto transactionDto) {
        try {
            Transaction transaction = getById(transactionDto.getTrxID());
            transaction.setStatus(TransactionStatus.FAILED);
            transaction.setDescription(transaction.getDescription());
            transactionRepository.save(transaction);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    private Transaction createTransactionObject(TransactionDto transactionDto) {
        Optional<Client> optionalClient = clientRepository.findById(transactionDto.getClientId());
        if (optionalClient.isPresent()) {
            return new Transaction(
                    null,
                    transactionDto.getTransactionType(),
                    TransactionStatus.NEW,
                    transactionDto.getAmount(),
                    new Date(System.currentTimeMillis()),
                    new Date(System.currentTimeMillis()),
                    null,
                    optionalClient.get()
            );
        }
        throw new EntityNotFoundException("Client with id: " + transactionDto.getClientId() + " does not exist");
    }

    private Transaction getById(Long id) {
        return transactionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Transaction with id: %s not found", id)));
    }
}
