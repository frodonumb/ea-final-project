package edu.miu.cs544.clientmanager.service.impl;


import edu.miu.cs544.clientmanager.configuration.RabbitMQConfiguration;
import edu.miu.cs544.clientmanager.dto.ClientDto;
import edu.miu.cs544.clientmanager.entity.Address;
import edu.miu.cs544.clientmanager.entity.Client;
import edu.miu.cs544.clientmanager.repository.ClientRepository;
import edu.miu.cs544.clientmanager.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public List<ClientDto> getAll() {
        return clientRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public ClientDto get(UUID id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            return toDto(optionalClient.get());
        }
        throw new EntityNotFoundException("Client with id: " + id + " does not exist");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public ClientDto create(ClientDto clientDto) {
        System.out.println(clientDto.toString());
        return toDto(clientRepository.save(fromDto(clientDto)));
    }
    @RabbitListener(queues = RabbitMQConfiguration.CLIENT_CREATED_QUEUE)
    public void onListenCreate(ClientDto clientDto){
        create(clientDto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public ClientDto update(UUID id, ClientDto clientDto) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            clientDto.setId(id);
            return toDto(clientRepository.save(fromDto(clientDto)));
        }
        throw new EntityNotFoundException("Client with id: " + id + " does not exist");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void delete(UUID id) {
        clientRepository.deleteById(id);
    }

    private ClientDto toDto(Client client) {
        return new ClientDto(
                client.getId(),
                client.getFirstname(),
                client.getLastname(),
                client.getEmail(),
                client.getAddress().getStreet(),
                client.getAddress().getCity(),
                client.getAddress().getState(),
                client.getAddress().getZip());
    }

    private Client fromDto(ClientDto clientDto) {
        return new Client(
                clientDto.getId(),
                clientDto.getFirstname(),
                clientDto.getLastname(),
                clientDto.getEmail(),
                new Address(
                        clientDto.getStreet(),
                        clientDto.getCity(),
                        clientDto.getState(),
                        clientDto.getZip())
        );
    }
}
