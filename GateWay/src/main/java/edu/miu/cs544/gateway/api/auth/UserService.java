package edu.miu.cs544.gateway.api.auth;

import edu.miu.cs544.gateway.dao.auth.UserRepo;
import edu.miu.cs544.gateway.domain.auth.LocalUser;
import edu.miu.cs544.gateway.domain.auth.Role;
import edu.miu.cs544.gateway.mapper.LocalUserMapper;
import edu.miu.cs544.gateway.rest.request.SignUpRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class UserService implements Users {

    private final UserRepo repo;
    private final LocalUserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LocalUser createUser(SignUpRequest request) {
        LocalUser localUser = new LocalUser();
        localUser.setUsername(request.getUsername());
        localUser.setPassword(passwordEncoder.encode(request.getPassword()));
        localUser.setRole(Role.CLIENT);
        //todo send message via mq to create client and client account
        return repo.save(localUser);
    }
}
