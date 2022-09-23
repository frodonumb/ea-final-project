package edu.miu.cs544.gateway.dto.auth;

import edu.miu.cs544.gateway.domain.auth.Role;
import lombok.Data;

@Data
public class UserDto {

    private String username;
    private Role role;
}
