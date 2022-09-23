package edu.miu.cs544.gateway.rest.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public interface AbstractRequest {

    @SneakyThrows
    default String toJsonString() {
        return new ObjectMapper().writeValueAsString(this);
    }
}
