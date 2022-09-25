package edu.miu.cs544.gateway.api.auth;

import edu.miu.cs544.gateway.dao.auth.UserRepo;
import edu.miu.cs544.gateway.domain.auth.LocalUser;
import edu.miu.cs544.gateway.domain.auth.Role;
import edu.miu.cs544.gateway.mapper.LocalUserMapper;
import edu.miu.cs544.gateway.rest.request.SignUpRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class UserService implements Users {

    private final UserRepo repo;
    private final LocalUserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public LocalUser createUser(SignUpRequest request) {
        LocalUser localUser = new LocalUser();
        localUser.setUsername(request.getUsername());
        localUser.setPassword(passwordEncoder.encode(request.getPassword()));
        localUser.setRole(Role.CLIENT);
        localUser = repo.saveAndFlush(localUser);
        rabbitTemplate.convertAndSend("CLIENT_CREATED", mapper.toDto(localUser));
        log.info("mq was sent with routing key CLIENT_CREATED");
        return localUser;
    }
}
