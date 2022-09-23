package edu.miu.cs544.clientmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto implements Serializable {
    private UUID userId;
    private String firstname;
    private String lastname;
    private String email;

    private String street;
    private String city;
    private String state;
    private String zip;
}
