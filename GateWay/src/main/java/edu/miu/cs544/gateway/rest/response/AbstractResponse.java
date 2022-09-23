package edu.miu.cs544.gateway.rest.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public interface AbstractResponse {

    @SneakyThrows
    default String toJsonString(){
        return new ObjectMapper().writeValueAsString(this);
    }
}
