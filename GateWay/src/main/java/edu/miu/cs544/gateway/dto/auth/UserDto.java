package edu.miu.cs544.gateway.dto.auth;

import edu.miu.cs544.gateway.domain.auth.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class UserDto implements Serializable {

    private UUID id;
    private String username;
    private Role role;
}
