package edu.miu.cs544.clientmanager.service.impl;

import edu.miu.cs544.dto.ClientDto;
import edu.miu.cs544.dto.TransactionDto;
import edu.miu.cs544.entity.Client;
import edu.miu.cs544.entity.Transaction;
import edu.miu.cs544.entity.enums.TransactionStatus;
import edu.miu.cs544.entity.enums.TransactionType;
import edu.miu.cs544.repository.ClientRepository;
import edu.miu.cs544.repository.TransactionRepository;
import edu.miu.cs544.service.ClientService;
import edu.miu.cs544.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository;
    private ClientRepository clientRepository;
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
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
