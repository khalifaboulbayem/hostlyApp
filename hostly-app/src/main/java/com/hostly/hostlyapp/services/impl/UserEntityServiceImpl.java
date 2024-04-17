package com.hostly.hostlyapp.services.impl;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hostly.hostlyapp.handlers.exceptions.BadRequestException;
import com.hostly.hostlyapp.handlers.exceptions.MalformedHeaderException;
import com.hostly.hostlyapp.handlers.exceptions.NotFoundException;
import com.hostly.hostlyapp.models.UserEntity;
import com.hostly.hostlyapp.models.dto.UserEntityDTO;
import com.hostly.hostlyapp.models.dto.response.UserResponse;
import com.hostly.hostlyapp.models.mappers.UserEntityMapper;
import com.hostly.hostlyapp.repositories.UserEntityRepository;
import com.hostly.hostlyapp.services.UserEntityService;

@Service
public class UserEntityServiceImpl implements UserEntityService {
    @Autowired
    private UserEntityRepository repository;

    @Autowired
    private UserEntityMapper mapper;

    @Override
    public UserResponse create(UserEntityDTO request) {

        UserEntity entity = mapper.userDTOtoUserEntity(request);
        try {
            repository.save(entity);
        } catch (Exception e) {
            throw new MalformedHeaderException(e.getMessage());
        }

        return mapper.userToUserResponse(entity);
    }

    @Override
    public UserResponse getById(UUID id) {
        if (id == null) {
            throw new BadRequestException("invalid id");
        }
        UserEntity entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find the user with ID: " + id));
        return mapper.userToUserResponse(entity);
    }

    @Override
    public Collection<UserResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::userToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse update(UUID id, UserEntityDTO request) {
        if (id == null) {
            throw new BadRequestException("Invalid id");
        }
        UserEntity existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("UserEntity not found"));

        // Actualization of fields
        existingEntity.setEmail(request.getEmail());
        // existingEntity.setPassword(request.getPassword());
        existingEntity.setName(request.getName());
        existingEntity.setRole(request.getRole());

        // save the entity in database
        UserEntity updatedUserEntity = repository.save(existingEntity);

        return mapper.userToUserResponse(updatedUserEntity);
    }

    @Override
    public void delete(UUID id) {
        if (id == null) {
            throw new BadRequestException("invalid id");
        }
        UserEntity user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("UserEntity not found"));
        repository.delete(user);
    }
}