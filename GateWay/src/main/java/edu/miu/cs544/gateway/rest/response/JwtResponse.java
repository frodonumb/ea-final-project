package edu.miu.cs544.gateway.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse implements AbstractResponse {

    private String token;

}
