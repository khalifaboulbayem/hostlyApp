package com.hostly.hostlyapp.models.mappers;

import org.mapstruct.Mapper;

import com.hostly.hostlyapp.models.UserEntity;
import com.hostly.hostlyapp.models.dto.UserEntityDTO;
import com.hostly.hostlyapp.models.dto.response.UserResponse;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    UserEntity userDTOtoUserEntity(UserEntityDTO userDTO);

    UserEntity userResponsetoUserEntity(UserResponse userDTO);

    UserEntityDTO userToUserEntityDTO(UserEntity user);

    UserResponse userToUserResponse(UserEntity user);
}
