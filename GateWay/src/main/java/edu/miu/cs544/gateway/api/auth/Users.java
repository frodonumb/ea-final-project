package edu.miu.cs544.gateway.api.auth;

import edu.miu.cs544.gateway.domain.auth.LocalUser;
import edu.miu.cs544.gateway.rest.request.SignUpRequest;

public interface Users {

    LocalUser createUser(SignUpRequest request);
}
