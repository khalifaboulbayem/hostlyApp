package com.hostly.hostlyapp.services.impl;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hostly.hostlyapp.handlers.exceptions.BadRequestException;
import com.hostly.hostlyapp.handlers.exceptions.NotFoundException;
import com.hostly.hostlyapp.models.User;
import com.hostly.hostlyapp.models.dto.UserDTO;
import com.hostly.hostlyapp.models.mappers.UserMapper;
import com.hostly.hostlyapp.repositories.UserRepository;
import com.hostly.hostlyapp.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    @Override
    public UserDTO create(UserDTO request) {
        User entity = mapper.userDTOtoUser(request);
        repository.save(entity);
        return mapper.userToUserDTO(entity);
    }

    @Override
    public UserDTO getById(UUID id) {
        if (id == null) {
            throw new BadRequestException("invalid id");
        }
        User entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return mapper.userToUserDTO(entity);
    }

    @Override
    public Collection<UserDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::userToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO update(UUID id, UserDTO request) {
        if (id == null) {
            throw new BadRequestException("Invalid id");
        }
        User existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Actualization of fields
        existingEntity.setEmail(request.getEmail());
        // existingEntity.setPassword(request.getPassword());
        existingEntity.setName(request.getName());
        existingEntity.setRole(request.getRole());

        // save the entity in database
        User updatedUser = repository.save(existingEntity);

        return mapper.userToUserDTO(updatedUser);
    }

    @Override
    public void delete(UUID id) {
        if (id == null) {
            throw new BadRequestException("invalid id");
        }
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        repository.delete(user);
    }
}