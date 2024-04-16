package com.hostly.hostlyapp.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hostly.hostlyapp.models.User;
import com.hostly.hostlyapp.models.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User userDTOtoUser(UserDTO userDTO);

    UserDTO userToUserDTO(User user);
}
