package edu.miu.cs544.gateway.mapper;

import edu.miu.cs544.gateway.domain.auth.LocalUser;
import edu.miu.cs544.gateway.dto.auth.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocalUserMapper {

    UserDto toDto(LocalUser user);

}
