package edu.miu.cs544.clientmanager.service.impl;


import edu.miu.cs544.clientmanager.dto.ClientDto;
import edu.miu.cs544.clientmanager.entity.Address;
import edu.miu.cs544.clientmanager.entity.Client;
import edu.miu.cs544.clientmanager.repository.ClientRepository;
import edu.miu.cs544.clientmanager.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;

    @Override
    public List<ClientDto> getAll() {
        return clientRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public ClientDto get(UUID id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            return toDto(optionalClient.get());
        }
        throw new EntityNotFoundException("Client with id: " + id + " does not exist");
    }

    @Override
    public ClientDto create(ClientDto clientDto) {
        return toDto(clientRepository.save(fromDto(clientDto)));
    }

    @Override
    public ClientDto update(UUID id, ClientDto clientDto) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            return toDto(clientRepository.save(fromDto(clientDto)));
        }
        throw new EntityNotFoundException("Client with id: " + id + " does not exist");
    }

    @Override
    public void delete(UUID id) {
        clientRepository.deleteById(id);
    }

    private ClientDto toDto(Client client) {
        return new ClientDto(
                client.getUserId(),
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
                clientDto.getUserId(), clientDto.getFirstname(), clientDto.getLastname(), clientDto.getEmail(),
                new Address(clientDto.getStreet(), clientDto.getCity(), clientDto.getState(), clientDto.getZip())
        );
    }
}
