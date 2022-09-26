package edu.miu.cs544.gateway.rest.request;

import lombok.Data;

@Data
public class SignUpRequest implements AbstractRequest {

    private String username;
    private String password;

    private String firstname;
    private String lastname;
    private String email;

    private String street;
    private String city;
    private String state;
    private String zip;

}
