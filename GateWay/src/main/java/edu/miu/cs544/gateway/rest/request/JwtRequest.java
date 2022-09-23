package edu.miu.cs544.gateway.rest.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class JwtRequest {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
