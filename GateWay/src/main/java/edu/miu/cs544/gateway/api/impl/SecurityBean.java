package edu.miu.cs544.gateway.api.impl;

import edu.miu.cs544.gateway.api.Security;
import edu.miu.cs544.gateway.dao.auth.UserRepo;
import edu.miu.cs544.gateway.domain.auth.LocalUser;
import edu.miu.cs544.gateway.exceptions.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Component
@AllArgsConstructor
@Transactional
public class SecurityBean implements Security {

    private final UserRepo userRepo;

    @Override
    public LocalUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return getByUsername(username).orElseThrow(() -> new UsernameNotFoundException(ErrorCode.USERNAME_NOT_FOUND.name()));
    }

    private Optional<LocalUser> getByUsername(String username) {
        return userRepo.findFirstByUsername(username);
    }

    @Override
    public LocalUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (ObjectUtils.isEmpty(authentication)) {
            throw new UsernameNotFoundException("Should be authenticated!");
        } else {
            return loadUserByUsername(authentication.getName());
        }
    }
}
