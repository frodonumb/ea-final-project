package edu.miu.cs544.clientmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    private UUID userId;
    private String firstname;
    private String lastname;
    private String email;
    @Embedded
    private Address address;
}