package edu.miu.cs544.gateway.mapper;

import edu.miu.cs544.gateway.dto.auth.ClientDto;
import edu.miu.cs544.gateway.rest.request.SignUpRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDto toClientDto(SignUpRequest request);
}
