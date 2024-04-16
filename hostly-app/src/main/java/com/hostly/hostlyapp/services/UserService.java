package com.hostly.hostlyapp.services;

import java.util.*;

import com.hostly.hostlyapp.models.dto.UserDTO;

public interface UserService {
    Collection<UserDTO> getAll();

    UserDTO getById(UUID id);

    UserDTO update(UUID id, UserDTO entityDto);

    UserDTO create(UserDTO entityDto);

    void delete(UUID id);

}
