package edu.miu.cs544.gateway.dto;

import java.io.Serializable;
import java.util.List;

public record ErrorDto(String code, List<Serializable> params) {

}
