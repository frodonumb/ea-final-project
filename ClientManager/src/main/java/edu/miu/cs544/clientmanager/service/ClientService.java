package edu.miu.cs544.clientmanager.service;


import edu.miu.cs544.clientmanager.dto.ClientDto;

import java.util.List;
import java.util.UUID;

public interface ClientService {
    List<ClientDto> getAll();

    ClientDto get(UUID id);

    ClientDto create(ClientDto clientDto);

    ClientDto update(UUID id, ClientDto clientDto);

    void delete(UUID id);
}
