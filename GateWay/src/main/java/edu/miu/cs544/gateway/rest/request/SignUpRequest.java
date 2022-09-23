package edu.miu.cs544.gateway.rest.request;

import lombok.Data;

@Data
public class SignUpRequest implements AbstractRequest {

    private String username;
    private String password;

}
