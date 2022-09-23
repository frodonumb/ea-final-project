package edu.miu.cs544.clientmanager.service.impl;

import edu.miu.cs544.clientmanager.dto.TransactionDto;
import edu.miu.cs544.clientmanager.entity.Client;
import edu.miu.cs544.clientmanager.entity.Transaction;
import edu.miu.cs544.clientmanager.entity.enums.TransactionStatus;
import edu.miu.cs544.clientmanager.repository.ClientRepository;
import edu.miu.cs544.clientmanager.repository.TransactionRepository;
import edu.miu.cs544.clientmanager.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository;
    private ClientRepository clientRepository;
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    @RabbitListener(queues = {"MAKE_TRANSACTION"})
    public void makeTransaction(TransactionDto transactionDto) {
        Transaction transaction = createTransactionObject(transactionDto);
        transactionRepository.save(transaction);
    }

    private Transaction createTransactionObject(TransactionDto transactionDto) {
        Optional<Client> optionalClient = clientRepository.findById(transactionDto.getClientId());
        if (optionalClient.isPresent()) {
            return new Transaction(
                    null,
                    transactionDto.getType(),
                    TransactionStatus.NEW,
                    transactionDto.getAmount(),
                    new Date(System.currentTimeMillis()),
                    new Date(System.currentTimeMillis()),
                    optionalClient.get()
            );
        }
        throw new EntityNotFoundException("Client with id: " + transactionDto.getClientId() + " does not exist");
    }
}
