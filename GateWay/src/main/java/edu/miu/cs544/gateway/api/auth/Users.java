package edu.miu.cs544.gateway.api.auth;

import edu.miu.cs544.gateway.domain.auth.LocalUser;
import edu.miu.cs544.gateway.dto.auth.ClientDto;
import edu.miu.cs544.gateway.rest.request.SignUpRequest;

import java.util.UUID;

public interface Users {

    LocalUser createUser(SignUpRequest request);

    LocalUser get(UUID id);

    ClientDto getClient(UUID id);

    ClientDto updateClient(UUID id, ClientDto clientDto);

    void deleteClient(UUID id);

    ClientDto getCurrentProfile();

    ClientDto updateCurrentProfile(ClientDto clientDto);

    void deleteCurrentProfile();


}
