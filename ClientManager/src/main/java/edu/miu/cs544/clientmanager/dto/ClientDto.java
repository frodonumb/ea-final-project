package edu.miu.cs544.clientmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClientDto implements Serializable {
    private UUID id;
    private String firstname;
    private String lastname;
    private String email;

    private String street;
    private String city;
    private String state;
    private String zip;
}
