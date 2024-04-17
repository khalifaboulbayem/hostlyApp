package com.hostly.hostlyapp.services;

import java.util.*;

import com.hostly.hostlyapp.models.dto.UserEntityDTO;
import com.hostly.hostlyapp.models.dto.response.UserResponse;

public interface UserEntityService {
    Collection<UserResponse> getAll();

    UserResponse getById(UUID id);

    UserResponse update(UUID id, UserEntityDTO entityDto);

    UserResponse create(UserEntityDTO entityDto);

    void delete(UUID id);

}
