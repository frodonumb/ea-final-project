package edu.miu.cs544.gateway.dao.auth;

import edu.miu.cs544.gateway.dao.SoftDeleteJpaRepository;
import edu.miu.cs544.gateway.domain.auth.LocalUser;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(propagation = Propagation.MANDATORY)
public interface UserRepo extends SoftDeleteJpaRepository<LocalUser> {

    Optional<LocalUser> findFirstByUsername(String username);
}
