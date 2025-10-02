package org.example.mappers;

import org.example.dtos.UserDto;
import org.example.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "age", source = "age")
    @Mapping(target = "createdAt", source = "createdAt")
    UserDto toDto(User user);
}
