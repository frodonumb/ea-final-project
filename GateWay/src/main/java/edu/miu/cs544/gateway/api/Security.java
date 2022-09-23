package edu.miu.cs544.gateway.api;

import edu.miu.cs544.gateway.domain.auth.LocalUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface Security extends UserDetailsService {

    LocalUser getCurrentUser();

}
