package edu.miu.cs544.accountmanager.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class UserDto implements Serializable {

    private UUID id;
    private String username;
}
