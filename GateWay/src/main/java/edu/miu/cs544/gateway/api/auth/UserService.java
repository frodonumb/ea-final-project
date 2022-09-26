package edu.miu.cs544.gateway.api.auth;

import edu.miu.cs544.gateway.api.Security;
import edu.miu.cs544.gateway.dao.auth.UserRepo;
import edu.miu.cs544.gateway.domain.auth.LocalUser;
import edu.miu.cs544.gateway.domain.auth.Role;
import edu.miu.cs544.gateway.dto.auth.ClientDto;
import edu.miu.cs544.gateway.mapper.ClientMapper;
import edu.miu.cs544.gateway.mapper.LocalUserMapper;
import edu.miu.cs544.gateway.rest.request.SignUpRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class UserService implements Users {

    private final UserRepo repo;
    private final LocalUserMapper mapper;

    private final ClientMapper clientMapper;
    private final PasswordEncoder passwordEncoder;
    private final RabbitTemplate rabbitTemplate;
    private final RestTemplate restTemplate;

    private final Security security;

    @Override
    public LocalUser createUser(SignUpRequest request) {
        LocalUser localUser = new LocalUser();
        localUser.setUsername(request.getUsername());
        localUser.setPassword(passwordEncoder.encode(request.getPassword()));
        localUser.setRole(Role.CLIENT);
        localUser = repo.saveAndFlush(localUser);
        ClientDto clientDto = clientMapper.toClientDto(request);
        clientDto.setId(localUser.getId());
        rabbitTemplate.convertAndSend("CLIENT_CREATED", clientDto);
        log.info("MQ was sent with routing key CLIENT_CREATED");
        return localUser;
    }

    @Override
    public LocalUser get(UUID id) {
        Optional<LocalUser> optionalLocalUser = repo.findById(id);
        if (optionalLocalUser.isPresent()) {
            return optionalLocalUser.get();
        }
        throw new EntityNotFoundException("User with id: " + id + " does not exist!");
    }

    @Override
    public ClientDto getClient(UUID id) {
        LocalUser localUser = get(id);
        return restTemplate.getForObject("http://localhost:8083/api/v1/clients/" + localUser.getId(), ClientDto.class);
    }

    @Override
    public ClientDto updateClient(UUID id, ClientDto clientDto) {
        LocalUser localUser = get(id);
        Map<String, UUID> params = new HashMap<>();
        params.put("id", localUser.getId());
        restTemplate.put("http://localhost:8083/api/v1/clients/" + localUser.getId(), clientDto, params);
        return getClient(localUser.getId());
    }

    @Override
    public void deleteClient(UUID id) {
        restTemplate.delete("http://localhost:8083/api/v1/clients/" + id);
    }

    @Override
    public ClientDto getCurrentProfile() {
        LocalUser user = security.getCurrentUser();
        return getClient(user.getId());
    }

    @Override
    public ClientDto updateCurrentProfile(ClientDto clientDto) {
        LocalUser user = security.getCurrentUser();
        return updateClient(user.getId(), clientDto);
    }

    @Override
    public void deleteCurrentProfile() {
        LocalUser user = security.getCurrentUser();
        deleteClient(user.getId());
    }
}
